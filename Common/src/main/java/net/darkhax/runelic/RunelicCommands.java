package net.darkhax.runelic;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.darkhax.runelic.mixin.AccessorSignBlockEntity;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class RunelicCommands {

    public static void registerCommands (CommandDispatcher<CommandSourceStack> dispatcher, boolean isDedicated) {

        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("runelic");

        // Allows everyone to post messages in chat using the font.
        root.then(Commands.literal("say").then(Commands.argument("text", StringArgumentType.greedyString()).executes(RunelicCommands::commandSay)));

        // Allows operators to rename the held item using the font.
        root.then(Commands.literal("hand").requires(p -> p.hasPermission(2)).executes(RunelicCommands::commandHand));

        // Book encode/decode command
        final LiteralArgumentBuilder<CommandSourceStack> book = Commands.literal("book").requires(p -> p.hasPermission(2));
        book.then(Commands.literal("encode").executes(ctx -> commandBook(ctx, Constants.FONT_RUNELIC)));
        book.then(Commands.literal("decode").executes(ctx -> commandBook(ctx, Constants.FONT_DEFAULT)));
        root.then(book);

        // Tile encode/decode command
        final LiteralArgumentBuilder<CommandSourceStack> tile = Commands.literal("tile").requires(p -> p.hasPermission(2));
        tile.then(Commands.literal("encode").then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> commandPos(ctx, Constants.FONT_RUNELIC))));
        tile.then(Commands.literal("decode").then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> commandPos(ctx, Constants.FONT_DEFAULT))));
        root.then(tile);

        dispatcher.register(root);
    }

    private static int commandSay (CommandContext<CommandSourceStack> ctx) {

        final Component inputMessage = applyFont(new TextComponent(StringArgumentType.getString(ctx, "text")), Constants.FONT_RUNELIC);
        final TranslatableComponent txtMessage = new TranslatableComponent("chat.type.announcement", ctx.getSource().getDisplayName(), inputMessage);
        final Entity sender = ctx.getSource().getEntity();

        if (sender != null) {

            ctx.getSource().getServer().getPlayerList().broadcastMessage(txtMessage, ChatType.CHAT, sender.getUUID());
        }

        else {

            ctx.getSource().getServer().getPlayerList().broadcastMessage(txtMessage, ChatType.SYSTEM, Util.NIL_UUID);
        }

        return 1;
    }

    private static int commandHand (CommandContext<CommandSourceStack> ctx) {

        final Entity sender = ctx.getSource().getEntity();

        if (sender instanceof LivingEntity living) {

            final ItemStack stack = living.getMainHandItem();
            stack.setHoverName(applyFont(stack.getHoverName(), Constants.FONT_RUNELIC));
        }

        return 1;
    }

    private static int commandPos (CommandContext<CommandSourceStack> ctx, ResourceLocation font) throws CommandSyntaxException {

        final ServerLevel world = ctx.getSource().getLevel();
        final BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");
        final BlockEntity tile = world.getBlockEntity(pos);

        if (tile != null) {

            if (tile instanceof BaseContainerBlockEntity container) {

                container.setCustomName(applyFont(container.getName(), font));
            }

            if (tile instanceof SignBlockEntity sign) {

                for (int i = 0; i < 4; i++) {

                    final Component component = sign.getMessage(i, false);

                    if (component != null) {

                        sign.setMessage(i, applyFont(component.copy(), font));
                    }
                }

                ((AccessorSignBlockEntity)sign).runelic$markUpdated();
            }
        }

        return 1;
    }

    private static int commandBook (CommandContext<CommandSourceStack> ctx, ResourceLocation font) throws CommandSyntaxException {

        final Entity sender = ctx.getSource().getEntity();

        if (sender instanceof LivingEntity living) {

            final ItemStack stack = living.getMainHandItem();

            if (stack.getItem() instanceof WrittenBookItem book && stack.hasTag()) {

                stack.setHoverName(applyFont(stack.getHoverName(), font));

                final CompoundTag stackTag = stack.getTag();

                if (stackTag != null) {

                    final ListTag pageData = stackTag.getList("pages", Tag.TAG_STRING);

                    for (int pageNum = 0; pageNum < pageData.size(); pageNum++) {

                        final Component pageText = Component.Serializer.fromJsonLenient(pageData.getString(pageNum));
                        applyFont(pageText, font);
                        pageData.set(pageNum, StringTag.valueOf(Component.Serializer.toJson(pageText)));
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
    private static Component applyFont (Component text, ResourceLocation font) {

        if (text instanceof MutableComponent mutable) {

            mutable.setStyle(text.getStyle().withFont(font));
        }

        text.getSiblings().forEach(sib -> applyFont(sib, font));
        return text;
    }
}