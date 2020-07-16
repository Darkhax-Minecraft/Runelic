package net.darkhax.runelic;

import java.util.UUID;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("runelic")
public class Runelic {
    
    /**
     * Font ID for the standard Runelic font. Currently supports standard alphanumeric
     * characters.
     */
    public static final ResourceLocation FONT_RUNELIC = new ResourceLocation("runelic", "runelic");
    
    /**
     * Font ID for the default Minecraft font.
     */
    public static final ResourceLocation FONT_DEFAULT = new ResourceLocation("minecraft", "default");
    
    public Runelic() {
        
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
    }
    
    private void registerCommands (RegisterCommandsEvent event) {
        
        final CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        
        final LiteralArgumentBuilder<CommandSource> root = Commands.literal("runelic");
        
        // Allows everyone to post messages in chat using the font.
        root.then(Commands.literal("say").then(Commands.argument("text", StringArgumentType.greedyString()).executes(this::commandSay)));
        
        // Allows operators to rename the held item using the font.
        root.then(Commands.literal("hand").requires(p -> p.hasPermissionLevel(2)).executes(this::commandHand));
        
        // Book encode/decode command
        final LiteralArgumentBuilder<CommandSource> book = Commands.literal("book").requires(p -> p.hasPermissionLevel(2));
        book.then(Commands.literal("encode").executes(ctx -> this.commandBook(ctx, FONT_RUNELIC)));
        book.then(Commands.literal("decode").executes(ctx -> this.commandBook(ctx, FONT_DEFAULT)));
        root.then(book);
        
        // Tile encode/decode command
        final LiteralArgumentBuilder<CommandSource> tile = Commands.literal("tile").requires(p -> p.hasPermissionLevel(2));
        tile.then(Commands.literal("encode").then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> this.commandPos(ctx, FONT_RUNELIC))));
        tile.then(Commands.literal("decode").then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> this.commandPos(ctx, FONT_DEFAULT))));
        root.then(tile);
        
        dispatcher.register(root);
    }
    
    private int commandSay (CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        
        final ITextComponent inputMessage = applyFont(new StringTextComponent(StringArgumentType.getString(ctx, "text")), FONT_RUNELIC);
        final TranslationTextComponent txtMessage = new TranslationTextComponent("chat.type.announcement", ctx.getSource().getDisplayName(), inputMessage);
        final Entity sender = ctx.getSource().getEntity();
        final ChatType chatType = sender != null ? ChatType.CHAT : ChatType.SYSTEM;
        final UUID senderId = sender != null ? sender.getUniqueID() : Util.DUMMY_UUID;
        
        ctx.getSource().getServer().getPlayerList().func_232641_a_(txtMessage, chatType, senderId);
        
        return 1;
    }
    
    private int commandHand (CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        
        final Entity sender = ctx.getSource().getEntity();
        
        if (sender instanceof LivingEntity) {
            
            final ItemStack stack = ((LivingEntity) sender).getHeldItemMainhand();
            stack.setDisplayName(applyFont(stack.getDisplayName(), FONT_RUNELIC));
        }
        
        return 1;
    }
    
    private int commandPos (CommandContext<CommandSource> ctx, ResourceLocation font) throws CommandSyntaxException {
        
        final ServerWorld world = ctx.getSource().getWorld();
        final BlockPos pos = BlockPosArgument.getBlockPos(ctx, "pos");
        final TileEntity tile = world.getTileEntity(pos);
        
        if (tile != null) {
            
            if (tile instanceof LockableTileEntity) {
                
                final LockableTileEntity lockable = (LockableTileEntity) tile;
                lockable.setCustomName(applyFont(lockable.getName(), font));
            }
            
            if (tile instanceof SignTileEntity) {
                
                final SignTileEntity sign = (SignTileEntity) tile;
                final CompoundNBT signTag = sign.write(new CompoundNBT());
                
                for (int line = 1; line <= 4; line++) {
                    
                    final String lineData = signTag.getString("Text" + line);
                    final ITextComponent lineText = applyFont(ITextComponent.Serializer.func_240643_a_(lineData.isEmpty() ? "\"\"" : lineData), font);
                    sign.setText(line - 1, lineText);
                }
                
                world.notifyBlockUpdate(pos, tile.getBlockState(), tile.getBlockState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
            }
        }
        
        return 1;
    }
    
    private int commandBook (CommandContext<CommandSource> ctx, ResourceLocation font) throws CommandSyntaxException {
        
        final Entity sender = ctx.getSource().getEntity();
        
        if (sender instanceof LivingEntity) {
            
            final ItemStack stack = ((LivingEntity) sender).getHeldItemMainhand();
            
            if (stack.getItem() instanceof WrittenBookItem && stack.hasTag()) {
                
                stack.setDisplayName(applyFont(stack.getDisplayName(), font));
                
                final CompoundNBT stackTag = stack.getTag();
                
                if (stackTag != null) {
                    
                    final ListNBT pageData = stackTag.getList("pages", NBT.TAG_STRING);
                    
                    for (int pageNum = 0; pageNum < pageData.size(); pageNum++) {
                        
                        final IFormattableTextComponent pageText = ITextComponent.Serializer.func_240644_b_(pageData.getString(pageNum));
                        applyFont(pageText, font);
                        pageData.set(pageNum, StringNBT.valueOf(ITextComponent.Serializer.toJson(pageText)));
                    }
                    
                    stackTag.put("pages", pageData);
                }
            }
        }
        
        return 1;
    }
    
    /**
     * Recursively applies a font to a text component and all of it's embedded text components.
     * 
     * @param text The text to apply a font to.
     * @param font The font to apply.
     * @return The text component that was passed in.
     */
    private static ITextComponent applyFont (ITextComponent text, ResourceLocation font) {
        
        if (text instanceof IFormattableTextComponent) {
            
            ((IFormattableTextComponent) text).func_230530_a_(text.getStyle().setFontId(font));
        }
        
        text.getSiblings().forEach(sib -> applyFont(sib, font));
        return text;
    }
}