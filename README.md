# [Runelic](https://www.curseforge.com/minecraft/mc-mods/runelic)
This mod allows the Runelic font to be used in Minecraft.

## Runelic Overview
Runelic is a character set which substitutes commonly used characters with runic symbols and designs. The runic symbols can be consistently translated back into their common counterparts. Uppercase and lowercase characters share the same designs. While some runic designs draw inspiration from real world languages and symbolism any associations between runelic designs and other characters are incidental. 

Currently supprted characters include `ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 0123456789 !"#$%&'()*+,-./`

![Runelic font displayed on some Minecraft signs.](https://i.imgur.com/iPOzEnZ.png "Runelic Translations")

## Usage Guide

### Custom Commands
If this mod is installed on a forge server the following commands will be made available.

- /runelic say <your message> - Posts a chat message using Runelic. This is available to all users.
- /runelic hand - Renames the held item to use Runelic. Limited to lvl 2 ops.
- /runelic book (encode|decode) - Rewrites a held book to use runelic. The decode command will turn it back to normal text. Limited to lvl 2 ops.
- /runelic tile (encode|decode) <pos> - Rewrites a tile entity such as a sign to use Runelic. The decode command will turn it back to normal. Limited to lvl 2 ops.

### Code
If you're developing a mod or have access to the raw item code you can apply runelic to an ITextComponent by modifying the style of the component.

**Example:**
```java
// MCP names not yet available
// func_240703_c_ = setStyle
// func_240719_a_ = setFont
IFormattableTextComponent text = new StringTextComponent("Hello World");
text.func_240703_c_(text.getStyle().func_240719_a_(new ResourceLocation("runelic", "runelic")));
```

### Vanilla Commands & NBT
Any vanilla command which allows you to define a text component can use Runelic by defining the ID in the text data. For example it can be used with the give command to give an item that uses the font for the item name.

**Example:**
```
/give @p stone{display:{Name:"[{\"text\":\"Hello\",\"italic\":false,\"color\":\"gold\",\"font\":\"runelic:runelic\"}]"}} 1
```

## Credits & Permissions
The Runelic font and character set was created by Tyler Hancock (Darkhax) and is all rights reserved. Projects which have a soft or hard software dependency on Runelic may use Runelic designs in screenshots, custom assets, character designs, and promotional material. 3rd party videos/blogs/articles/showcases may use Runelic designs in their own promotional material and coverage of projects which have a soft or hard software dependency on Runelic.