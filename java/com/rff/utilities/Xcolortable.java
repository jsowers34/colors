package com.rff.utilities;

import javax.swing.*;
import com.rff.utilities.RFFUtilities;
import com.rff.utilities.SortedVector;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
   <PRE>`
 *****************************************************************************
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF"><TD><B>PURPOSE</B></TD></TR></TABLE>
 * This class maps the standard colors defined in X (usually as rgb.txt) to
 * the Color class.  It permits searching for an X-color such as "AliceBlue"
 * and returning the appropriate Color.
 *****************************************************************************
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF"><TD><B>MODIFICATIONS</B></TD></TR></TABLE>
 * <PRE>
 *
 *     CH03      JL Sowers        08/09/2004
 *               Add ability to look up correct name; add window closing code
 *     CH02      JL Sowers        04/22/2004
 *               Made tables static so that methods could also be static
 *     CH01      JL Sowers        10/29/2001
 *               Add a method which returns a SortedVector of color names.
 *               Aligned table for easier reading. Added hex values as comments.
 *     @author   JL Sowers        08/21/2001
 * </PRE>
 *****************************************************************************
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0" WIDTH="100%">
 * <TR BGCOLOR="#EEEEFF"><TD><B>DESIGN NOTES:</B></TD></TR></TABLE>
 *
 *     @version $Revision: 6.3 $
 *****************************************************************************
 </PRE>
*/
public class Xcolortable implements ChangeListener, ItemListener, WindowListener {
    // Constants
    public final static String		   rcsid    = "$Header: /export/home4/jsowers/tmlog/dev/RCS/Xcolortable.java,v 6.3 2004/08/10 18:42:07 jsowers Exp $";

    // Data
    private static HashMap<String, Color>  colorMap = new HashMap<String, Color>();									      // CH01
    private static HashMap<String, String> nameMap  = new HashMap<String, String>();									      // CHxx

    // SCH02
    private JSlider			   redSlider;
    private JSlider			   blueSlider;
    private JSlider			   greenSlider;
    private JTextArea			   colorArea;
    private JComboBox<String>		   colorName;
    private JLabel			   hexValue;
    private JFrame			   frame    = null;
    // ECH02

    // SCHxx
    static {
	colorMap.put("**** UNKNOWN ****", null); // Non-existant
	colorMap.put("AliceBlue", new Color(240, 248, 255)); // 0xf0f8ff
	colorMap.put("AntiqueWhite", new Color(250, 235, 215)); // 0xfaebd7
	colorMap.put("AntiqueWhite1", new Color(255, 239, 219)); // 0xffefdb
	colorMap.put("AntiqueWhite2", new Color(238, 223, 204)); // 0xeedfcc
	colorMap.put("AntiqueWhite3", new Color(205, 192, 176)); // 0xcdc0b0
	colorMap.put("AntiqueWhite4", new Color(139, 131, 120)); // 0x8b8378
	colorMap.put("Aquamarine", new Color(50, 191, 193)); // 0x32bfc1
	colorMap.put("Black", new Color(0, 0, 0)); // 0x000000
	colorMap.put("BlanchedAlmond", new Color(255, 235, 205)); // 0xffebcd
	colorMap.put("Blue", new Color(0, 0, 255)); // 0x0000ff
	colorMap.put("BlueViolet", new Color(138, 43, 226)); // 0x8a2be2
	colorMap.put("BrickRed", new Color(128, 0, 0)); // 0x800000 CHxx
	colorMap.put("Brown", new Color(165, 42, 42)); // 0xa52a2a
	colorMap.put("CadetBlue", new Color(95, 146, 158)); // 0x5f929e
	colorMap.put("CadetBlue1", new Color(152, 245, 255)); // 0x98f5ff
	colorMap.put("CadetBlue2", new Color(142, 229, 238)); // 0x8ee5ee
	colorMap.put("CadetBlue3", new Color(122, 197, 205)); // 0x7ac5cd
	colorMap.put("CadetBlue4", new Color(83, 134, 139)); // 0x53868b
	colorMap.put("Coral", new Color(255, 114, 86)); // 0xff7256
	colorMap.put("CornflowerBlue", new Color(34, 34, 152)); // 0x222298
	colorMap.put("Cyan", new Color(0, 255, 255)); // 0x00ffff
	colorMap.put("DarkGoldenrod", new Color(184, 134, 11)); // 0xb8860b
	colorMap.put("DarkGoldenrod1", new Color(255, 185, 15)); // 0xffb90f
	colorMap.put("DarkGoldenrod2", new Color(238, 173, 14)); // 0xeead0e
	colorMap.put("DarkGoldenrod3", new Color(205, 149, 12)); // 0xcd950c
	colorMap.put("DarkGoldenrod4", new Color(139, 101, 8)); // 0x8b6508
	colorMap.put("DarkGreen", new Color(0, 86, 45)); // 0x00562d
	colorMap.put("DarkKhaki", new Color(189, 183, 107)); // 0xbdb76b
	colorMap.put("DarkOliveGreen", new Color(85, 86, 47)); // 0x55562f
	colorMap.put("DarkOliveGreen1", new Color(202, 255, 112)); // 0xcaff70
	colorMap.put("DarkOliveGreen2", new Color(188, 238, 104)); // 0xbcee68
	colorMap.put("DarkOliveGreen3", new Color(162, 205, 90)); // 0xa2cd5a
	colorMap.put("DarkOliveGreen4", new Color(110, 139, 61)); // 0x6e8b3d
	colorMap.put("DarkOrange", new Color(255, 140, 0)); // 0xff8c00
	colorMap.put("DarkOrange1", new Color(255, 127, 0)); // 0xff7f00
	colorMap.put("DarkOrange2", new Color(238, 118, 0)); // 0xee7600
	colorMap.put("DarkOrange3", new Color(205, 102, 0)); // 0xcd6600
	colorMap.put("DarkOrange4", new Color(139, 69, 0)); // 0x8b4500
	colorMap.put("DarkOrchid", new Color(139, 32, 139)); // 0x8b208b
	colorMap.put("DarkOrchid1", new Color(191, 62, 255)); // 0xbf3eff
	colorMap.put("DarkOrchid2", new Color(178, 58, 238)); // 0xb23aee
	colorMap.put("DarkOrchid3", new Color(154, 50, 205)); // 0x9a32cd
	colorMap.put("DarkOrchid4", new Color(104, 34, 139)); // 0x68228b
	colorMap.put("DarkSalmon", new Color(233, 150, 122)); // 0xe9967a
	colorMap.put("DarkSeaGreen", new Color(143, 188, 143)); // 0x8fbc8f
	colorMap.put("DarkSeaGreen1", new Color(193, 255, 193)); // 0xc1ffc1
	colorMap.put("DarkSeaGreen2", new Color(180, 238, 180)); // 0xb4eeb4
	colorMap.put("DarkSeaGreen3", new Color(155, 205, 155)); // 0x9bcd9b
	colorMap.put("DarkSeaGreen4", new Color(105, 139, 105)); // 0x698b69
	colorMap.put("DarkSlateBlue", new Color(56, 75, 102)); // 0x384b66
	colorMap.put("DarkSlateGray", new Color(47, 79, 79)); // 0x2f4f4f
	colorMap.put("DarkSlateGray1", new Color(151, 255, 255)); // 0x97ffff
	colorMap.put("DarkSlateGray2", new Color(141, 238, 238)); // 0x8deeee
	colorMap.put("DarkSlateGray3", new Color(121, 205, 205)); // 0x79cdcd
	colorMap.put("DarkSlateGray4", new Color(82, 139, 139)); // 0x528b8b
	colorMap.put("DarkSlateGrey", new Color(47, 79, 79)); // 0x2f4f4f
	colorMap.put("DarkTurquoise", new Color(0, 166, 166)); // 0x00a6a6
	colorMap.put("DarkViolet", new Color(148, 0, 211)); // 0x9400d3
	colorMap.put("DeepPink", new Color(255, 20, 147)); // 0xff1493
	colorMap.put("DeepPink1", new Color(255, 20, 147)); // 0xff1493
	colorMap.put("DeepPink2", new Color(238, 18, 137)); // 0xee1289
	colorMap.put("DeepPink3", new Color(205, 16, 118)); // 0xcd1076
	colorMap.put("DeepPink4", new Color(139, 10, 80)); // 0x8b0a50
	colorMap.put("DeepSkyBlue", new Color(0, 191, 255)); // 0x00bfff
	colorMap.put("DeepSkyBlue1", new Color(0, 191, 255)); // 0x00bfff
	colorMap.put("DeepSkyBlue2", new Color(0, 178, 238)); // 0x00b2ee
	colorMap.put("DeepSkyBlue3", new Color(0, 154, 205)); // 0x009acd
	colorMap.put("DeepSkyBlue4", new Color(0, 104, 139)); // 0x00688b
	colorMap.put("DimGray", new Color(84, 84, 84)); // 0x545454
	colorMap.put("DimGrey", new Color(84, 84, 84)); // 0x545454
	colorMap.put("DodgerBlue", new Color(30, 144, 255)); // 0x1e90ff
	colorMap.put("DodgerBlue1", new Color(30, 144, 255)); // 0x1e90ff
	colorMap.put("DodgerBlue2", new Color(28, 134, 238)); // 0x1c86ee
	colorMap.put("DodgerBlue3", new Color(24, 116, 205)); // 0x1874cd
	colorMap.put("DodgerBlue4", new Color(16, 78, 139)); // 0x104e8b
	colorMap.put("Firebrick", new Color(142, 35, 35)); // 0x8e2323
	colorMap.put("FloralWhite", new Color(255, 250, 240)); // 0xfffaf0
	colorMap.put("ForestGreen", new Color(80, 159, 105)); // 0x509f69
	colorMap.put("GhostWhite", new Color(248, 248, 255)); // 0xf8f8ff
	colorMap.put("Gold", new Color(218, 170, 0)); // 0xdaaa00
	colorMap.put("Goldenrod", new Color(239, 223, 132)); // 0xefdf84
	colorMap.put("Gray", new Color(126, 126, 126)); // 0x7e7e7e
	colorMap.put("Gray0", new Color(0, 0, 0)); // 0x000000
	colorMap.put("Gray1", new Color(3, 3, 3)); // 0x030303
	colorMap.put("Gray10", new Color(26, 26, 26)); // 0x1a1a1a
	colorMap.put("Gray100", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("Gray11", new Color(28, 28, 28)); // 0x1c1c1c
	colorMap.put("Gray12", new Color(31, 31, 31)); // 0x1f1f1f
	colorMap.put("Gray13", new Color(33, 33, 33)); // 0x212121
	colorMap.put("Gray14", new Color(36, 36, 36)); // 0x242424
	colorMap.put("Gray15", new Color(38, 38, 38)); // 0x262626
	colorMap.put("Gray16", new Color(41, 41, 41)); // 0x292929
	colorMap.put("Gray17", new Color(43, 43, 43)); // 0x2b2b2b
	colorMap.put("Gray18", new Color(46, 46, 46)); // 0x2e2e2e
	colorMap.put("Gray19", new Color(48, 48, 48)); // 0x303030
	colorMap.put("Gray2", new Color(5, 5, 5)); // 0x050505
	colorMap.put("Gray20", new Color(51, 51, 51)); // 0x333333
	colorMap.put("Gray21", new Color(54, 54, 54)); // 0x363636
	colorMap.put("Gray22", new Color(56, 56, 56)); // 0x383838
	colorMap.put("Gray23", new Color(59, 59, 59)); // 0x3b3b3b
	colorMap.put("Gray24", new Color(61, 61, 61)); // 0x3d3d3d
	colorMap.put("Gray25", new Color(64, 64, 64)); // 0x404040
	colorMap.put("Gray26", new Color(66, 66, 66)); // 0x424242
	colorMap.put("Gray27", new Color(69, 69, 69)); // 0x454545
	colorMap.put("Gray28", new Color(71, 71, 71)); // 0x474747
	colorMap.put("Gray29", new Color(74, 74, 74)); // 0x4a4a4a
	colorMap.put("Gray3", new Color(8, 8, 8)); // 0x080808
	colorMap.put("Gray30", new Color(77, 77, 77)); // 0x4d4d4d
	colorMap.put("Gray31", new Color(79, 79, 79)); // 0x4f4f4f
	colorMap.put("Gray32", new Color(82, 82, 82)); // 0x525252
	colorMap.put("Gray33", new Color(84, 84, 84)); // 0x545454
	colorMap.put("Gray34", new Color(87, 87, 87)); // 0x575757
	colorMap.put("Gray35", new Color(89, 89, 89)); // 0x595959
	colorMap.put("Gray36", new Color(92, 92, 92)); // 0x5c5c5c
	colorMap.put("Gray37", new Color(94, 94, 94)); // 0x5e5e5e
	colorMap.put("Gray38", new Color(97, 97, 97)); // 0x616161
	colorMap.put("Gray39", new Color(99, 99, 99)); // 0x636363
	colorMap.put("Gray4", new Color(10, 10, 10)); // 0x0a0a0a
	colorMap.put("Gray40", new Color(102, 102, 102)); // 0x666666
	colorMap.put("Gray41", new Color(105, 105, 105)); // 0x696969
	colorMap.put("Gray42", new Color(107, 107, 107)); // 0x6b6b6b
	colorMap.put("Gray43", new Color(110, 110, 110)); // 0x6e6e6e
	colorMap.put("Gray44", new Color(112, 112, 112)); // 0x707070
	colorMap.put("Gray45", new Color(115, 115, 115)); // 0x737373
	colorMap.put("Gray46", new Color(117, 117, 117)); // 0x757575
	colorMap.put("Gray47", new Color(120, 120, 120)); // 0x787878
	colorMap.put("Gray48", new Color(122, 122, 122)); // 0x7a7a7a
	colorMap.put("Gray49", new Color(125, 125, 125)); // 0x7d7d7d
	colorMap.put("Gray5", new Color(13, 13, 13)); // 0x0d0d0d
	colorMap.put("Gray50", new Color(127, 127, 127)); // 0x7f7f7f
	colorMap.put("Gray51", new Color(130, 130, 130)); // 0x828282
	colorMap.put("Gray52", new Color(133, 133, 133)); // 0x858585
	colorMap.put("Gray53", new Color(135, 135, 135)); // 0x878787
	colorMap.put("Gray54", new Color(138, 138, 138)); // 0x8a8a8a
	colorMap.put("Gray55", new Color(140, 140, 140)); // 0x8c8c8c
	colorMap.put("Gray56", new Color(143, 143, 143)); // 0x8f8f8f
	colorMap.put("Gray57", new Color(145, 145, 145)); // 0x919191
	colorMap.put("Gray58", new Color(148, 148, 148)); // 0x949494
	colorMap.put("Gray59", new Color(150, 150, 150)); // 0x969696
	colorMap.put("Gray6", new Color(15, 15, 15)); // 0x0f0f0f
	colorMap.put("Gray60", new Color(153, 153, 153)); // 0x999999
	colorMap.put("Gray61", new Color(156, 156, 156)); // 0x9c9c9c
	colorMap.put("Gray62", new Color(158, 158, 158)); // 0x9e9e9e
	colorMap.put("Gray63", new Color(161, 161, 161)); // 0xa1a1a1
	colorMap.put("Gray64", new Color(163, 163, 163)); // 0xa3a3a3
	colorMap.put("Gray65", new Color(166, 166, 166)); // 0xa6a6a6
	colorMap.put("Gray66", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("Gray67", new Color(171, 171, 171)); // 0xababab
	colorMap.put("Gray68", new Color(173, 173, 173)); // 0xadadad
	colorMap.put("Gray69", new Color(176, 176, 176)); // 0xb0b0b0
	colorMap.put("Gray7", new Color(18, 18, 18)); // 0x121212
	colorMap.put("Gray70", new Color(179, 179, 179)); // 0xb3b3b3
	colorMap.put("Gray71", new Color(181, 181, 181)); // 0xb5b5b5
	colorMap.put("Gray72", new Color(184, 184, 184)); // 0xb8b8b8
	colorMap.put("Gray73", new Color(186, 186, 186)); // 0xbababa
	colorMap.put("Gray74", new Color(189, 189, 189)); // 0xbdbdbd
	colorMap.put("Gray75", new Color(191, 191, 191)); // 0xbfbfbf
	colorMap.put("Gray76", new Color(194, 194, 194)); // 0xc2c2c2
	colorMap.put("Gray77", new Color(196, 196, 196)); // 0xc4c4c4
	colorMap.put("Gray78", new Color(199, 199, 199)); // 0xc7c7c7
	colorMap.put("Gray79", new Color(201, 201, 201)); // 0xc9c9c9
	colorMap.put("Gray8", new Color(20, 20, 20)); // 0x141414
	colorMap.put("Gray80", new Color(204, 204, 204)); // 0xcccccc
	colorMap.put("Gray81", new Color(207, 207, 207)); // 0xcfcfcf
	colorMap.put("Gray82", new Color(209, 209, 209)); // 0xd1d1d1
	colorMap.put("Gray83", new Color(212, 212, 212)); // 0xd4d4d4
	colorMap.put("Gray84", new Color(214, 214, 214)); // 0xd6d6d6
	colorMap.put("Gray85", new Color(217, 217, 217)); // 0xd9d9d9
	colorMap.put("Gray86", new Color(219, 219, 219)); // 0xdbdbdb
	colorMap.put("Gray87", new Color(222, 222, 222)); // 0xdedede
	colorMap.put("Gray88", new Color(224, 224, 224)); // 0xe0e0e0
	colorMap.put("Gray89", new Color(227, 227, 227)); // 0xe3e3e3
	colorMap.put("Gray9", new Color(23, 23, 23)); // 0x171717
	colorMap.put("Gray90", new Color(229, 229, 229)); // 0xe5e5e5
	colorMap.put("Gray91", new Color(232, 232, 232)); // 0xe8e8e8
	colorMap.put("Gray92", new Color(235, 235, 235)); // 0xebebeb
	colorMap.put("Gray93", new Color(237, 237, 237)); // 0xededed
	colorMap.put("Gray94", new Color(240, 240, 240)); // 0xf0f0f0
	colorMap.put("Gray95", new Color(242, 242, 242)); // 0xf2f2f2
	colorMap.put("Gray96", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("Gray97", new Color(247, 247, 247)); // 0xf7f7f7
	colorMap.put("Gray98", new Color(250, 250, 250)); // 0xfafafa
	colorMap.put("Gray99", new Color(252, 252, 252)); // 0xfcfcfc
	colorMap.put("Green", new Color(0, 255, 0)); // 0x00ff00
	colorMap.put("GreenYellow", new Color(173, 255, 47)); // 0xadff2f
	colorMap.put("Grey", new Color(126, 126, 126)); // 0x7e7e7e
	colorMap.put("Grey0", new Color(0, 0, 0)); // 0x000000
	colorMap.put("Grey1", new Color(3, 3, 3)); // 0x030303
	colorMap.put("Grey10", new Color(26, 26, 26)); // 0x1a1a1a
	colorMap.put("Grey100", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("Grey11", new Color(28, 28, 28)); // 0x1c1c1c
	colorMap.put("Grey12", new Color(31, 31, 31)); // 0x1f1f1f
	colorMap.put("Grey13", new Color(33, 33, 33)); // 0x212121
	colorMap.put("Grey14", new Color(36, 36, 36)); // 0x242424
	colorMap.put("Grey15", new Color(38, 38, 38)); // 0x262626
	colorMap.put("Grey16", new Color(41, 41, 41)); // 0x292929
	colorMap.put("Grey17", new Color(43, 43, 43)); // 0x2b2b2b
	colorMap.put("Grey18", new Color(46, 46, 46)); // 0x2e2e2e
	colorMap.put("Grey19", new Color(48, 48, 48)); // 0x303030
	colorMap.put("Grey2", new Color(5, 5, 5)); // 0x050505
	colorMap.put("Grey20", new Color(51, 51, 51)); // 0x333333
	colorMap.put("Grey21", new Color(54, 54, 54)); // 0x363636
	colorMap.put("Grey22", new Color(56, 56, 56)); // 0x383838
	colorMap.put("Grey23", new Color(59, 59, 59)); // 0x3b3b3b
	colorMap.put("Grey24", new Color(61, 61, 61)); // 0x3d3d3d
	colorMap.put("Grey25", new Color(64, 64, 64)); // 0x404040
	colorMap.put("Grey26", new Color(66, 66, 66)); // 0x424242
	colorMap.put("Grey27", new Color(69, 69, 69)); // 0x454545
	colorMap.put("Grey28", new Color(71, 71, 71)); // 0x474747
	colorMap.put("Grey29", new Color(74, 74, 74)); // 0x4a4a4a
	colorMap.put("Grey3", new Color(8, 8, 8)); // 0x080808
	colorMap.put("Grey30", new Color(77, 77, 77)); // 0x4d4d4d
	colorMap.put("Grey31", new Color(79, 79, 79)); // 0x4f4f4f
	colorMap.put("Grey32", new Color(82, 82, 82)); // 0x525252
	colorMap.put("Grey33", new Color(84, 84, 84)); // 0x545454
	colorMap.put("Grey34", new Color(87, 87, 87)); // 0x575757
	colorMap.put("Grey35", new Color(89, 89, 89)); // 0x595959
	colorMap.put("Grey36", new Color(92, 92, 92)); // 0x5c5c5c
	colorMap.put("Grey37", new Color(94, 94, 94)); // 0x5e5e5e
	colorMap.put("Grey38", new Color(97, 97, 97)); // 0x616161
	colorMap.put("Grey39", new Color(99, 99, 99)); // 0x636363
	colorMap.put("Grey4", new Color(10, 10, 10)); // 0x0a0a0a
	colorMap.put("Grey40", new Color(102, 102, 102)); // 0x666666
	colorMap.put("Grey41", new Color(105, 105, 105)); // 0x696969
	colorMap.put("Grey42", new Color(107, 107, 107)); // 0x6b6b6b
	colorMap.put("Grey43", new Color(110, 110, 110)); // 0x6e6e6e
	colorMap.put("Grey44", new Color(112, 112, 112)); // 0x707070
	colorMap.put("Grey45", new Color(115, 115, 115)); // 0x737373
	colorMap.put("Grey46", new Color(117, 117, 117)); // 0x757575
	colorMap.put("Grey47", new Color(120, 120, 120)); // 0x787878
	colorMap.put("Grey48", new Color(122, 122, 122)); // 0x7a7a7a
	colorMap.put("Grey49", new Color(125, 125, 125)); // 0x7d7d7d
	colorMap.put("Grey5", new Color(13, 13, 13)); // 0x0d0d0d
	colorMap.put("Grey50", new Color(127, 127, 127)); // 0x7f7f7f
	colorMap.put("Grey51", new Color(130, 130, 130)); // 0x828282
	colorMap.put("Grey52", new Color(133, 133, 133)); // 0x858585
	colorMap.put("Grey53", new Color(135, 135, 135)); // 0x878787
	colorMap.put("Grey54", new Color(138, 138, 138)); // 0x8a8a8a
	colorMap.put("Grey55", new Color(140, 140, 140)); // 0x8c8c8c
	colorMap.put("Grey56", new Color(143, 143, 143)); // 0x8f8f8f
	colorMap.put("Grey57", new Color(145, 145, 145)); // 0x919191
	colorMap.put("Grey58", new Color(148, 148, 148)); // 0x949494
	colorMap.put("Grey59", new Color(150, 150, 150)); // 0x969696
	colorMap.put("Grey6", new Color(15, 15, 15)); // 0x0f0f0f
	colorMap.put("Grey60", new Color(153, 153, 153)); // 0x999999
	colorMap.put("Grey61", new Color(156, 156, 156)); // 0x9c9c9c
	colorMap.put("Grey62", new Color(158, 158, 158)); // 0x9e9e9e
	colorMap.put("Grey63", new Color(161, 161, 161)); // 0xa1a1a1
	colorMap.put("Grey64", new Color(163, 163, 163)); // 0xa3a3a3
	colorMap.put("Grey65", new Color(166, 166, 166)); // 0xa6a6a6
	colorMap.put("Grey66", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("Grey67", new Color(171, 171, 171)); // 0xababab
	colorMap.put("Grey68", new Color(173, 173, 173)); // 0xadadad
	colorMap.put("Grey69", new Color(176, 176, 176)); // 0xb0b0b0
	colorMap.put("Grey7", new Color(18, 18, 18)); // 0x121212
	colorMap.put("Grey70", new Color(179, 179, 179)); // 0xb3b3b3
	colorMap.put("Grey71", new Color(181, 181, 181)); // 0xb5b5b5
	colorMap.put("Grey72", new Color(184, 184, 184)); // 0xb8b8b8
	colorMap.put("Grey73", new Color(186, 186, 186)); // 0xbababa
	colorMap.put("Grey74", new Color(189, 189, 189)); // 0xbdbdbd
	colorMap.put("Grey75", new Color(191, 191, 191)); // 0xbfbfbf
	colorMap.put("Grey76", new Color(194, 194, 194)); // 0xc2c2c2
	colorMap.put("Grey77", new Color(196, 196, 196)); // 0xc4c4c4
	colorMap.put("Grey78", new Color(199, 199, 199)); // 0xc7c7c7
	colorMap.put("Grey79", new Color(201, 201, 201)); // 0xc9c9c9
	colorMap.put("Grey8", new Color(20, 20, 20)); // 0x141414
	colorMap.put("Grey80", new Color(204, 204, 204)); // 0xcccccc
	colorMap.put("Grey81", new Color(207, 207, 207)); // 0xcfcfcf
	colorMap.put("Grey82", new Color(209, 209, 209)); // 0xd1d1d1
	colorMap.put("Grey83", new Color(212, 212, 212)); // 0xd4d4d4
	colorMap.put("Grey84", new Color(214, 214, 214)); // 0xd6d6d6
	colorMap.put("Grey85", new Color(217, 217, 217)); // 0xd9d9d9
	colorMap.put("Grey86", new Color(219, 219, 219)); // 0xdbdbdb
	colorMap.put("Grey87", new Color(222, 222, 222)); // 0xdedede
	colorMap.put("Grey88", new Color(224, 224, 224)); // 0xe0e0e0
	colorMap.put("Grey89", new Color(227, 227, 227)); // 0xe3e3e3
	colorMap.put("Grey9", new Color(23, 23, 23)); // 0x171717
	colorMap.put("Grey90", new Color(229, 229, 229)); // 0xe5e5e5
	colorMap.put("Grey91", new Color(232, 232, 232)); // 0xe8e8e8
	colorMap.put("Grey92", new Color(235, 235, 235)); // 0xebebeb
	colorMap.put("Grey93", new Color(237, 237, 237)); // 0xededed
	colorMap.put("Grey94", new Color(240, 240, 240)); // 0xf0f0f0
	colorMap.put("Grey95", new Color(242, 242, 242)); // 0xf2f2f2
	colorMap.put("Grey96", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("Grey97", new Color(247, 247, 247)); // 0xf7f7f7
	colorMap.put("Grey98", new Color(250, 250, 250)); // 0xfafafa
	colorMap.put("Grey99", new Color(252, 252, 252)); // 0xfcfcfc
	colorMap.put("HotPink", new Color(255, 105, 180)); // 0xff69b4
	colorMap.put("HotPink1", new Color(255, 110, 180)); // 0xff6eb4
	colorMap.put("HotPink2", new Color(238, 106, 167)); // 0xee6aa7
	colorMap.put("HotPink3", new Color(205, 96, 144)); // 0xcd6090
	colorMap.put("HotPink4", new Color(139, 58, 98)); // 0x8b3a62
	colorMap.put("IndianRed", new Color(107, 57, 57)); // 0x6b3939
	colorMap.put("IndianRed1", new Color(255, 106, 106)); // 0xff6a6a
	colorMap.put("IndianRed2", new Color(238, 99, 99)); // 0xee6363
	colorMap.put("IndianRed3", new Color(205, 85, 85)); // 0xcd5555
	colorMap.put("IndianRed4", new Color(139, 58, 58)); // 0x8b3a3a
	colorMap.put("Khaki", new Color(179, 179, 126)); // 0xb3b37e
	colorMap.put("LavenderBlush", new Color(255, 240, 245)); // 0xfff0f5
	colorMap.put("LavenderBlush1", new Color(255, 240, 245)); // 0xfff0f5
	colorMap.put("LavenderBlush2", new Color(238, 224, 229)); // 0xeee0e5
	colorMap.put("LavenderBlush3", new Color(205, 193, 197)); // 0xcdc1c5
	colorMap.put("LavenderBlush4", new Color(139, 131, 134)); // 0x8b8386
	colorMap.put("LawnGreen", new Color(124, 252, 0)); // 0x7cfc00
	colorMap.put("LemonChiffon", new Color(255, 250, 205)); // 0xfffacd
	colorMap.put("LemonChiffon1", new Color(255, 250, 205)); // 0xfffacd
	colorMap.put("LemonChiffon2", new Color(238, 233, 191)); // 0xeee9bf
	colorMap.put("LemonChiffon3", new Color(205, 201, 165)); // 0xcdc9a5
	colorMap.put("LemonChiffon4", new Color(139, 137, 112)); // 0x8b8970
	colorMap.put("LightBlue", new Color(176, 226, 255)); // 0xb0e2ff
	colorMap.put("LightBlue1", new Color(191, 239, 255)); // 0xbfefff
	colorMap.put("LightBlue2", new Color(178, 223, 238)); // 0xb2dfee
	colorMap.put("LightBlue3", new Color(154, 192, 205)); // 0x9ac0cd
	colorMap.put("LightBlue4", new Color(104, 131, 139)); // 0x68838b
	colorMap.put("LightCoral", new Color(240, 128, 128)); // 0xf08080
	colorMap.put("LightCyan", new Color(224, 255, 255)); // 0xe0ffff
	colorMap.put("LightCyan1", new Color(224, 255, 255)); // 0xe0ffff
	colorMap.put("LightCyan2", new Color(209, 238, 238)); // 0xd1eeee
	colorMap.put("LightCyan3", new Color(180, 205, 205)); // 0xb4cdcd
	colorMap.put("LightCyan4", new Color(122, 139, 139)); // 0x7a8b8b
	colorMap.put("LightGoldenrod", new Color(238, 221, 130)); // 0xeedd82
	colorMap.put("LightGoldenrod1", new Color(255, 236, 139)); // 0xffec8b
	colorMap.put("LightGoldenrod2", new Color(238, 220, 130)); // 0xeedc82
	colorMap.put("LightGoldenrod3", new Color(205, 190, 112)); // 0xcdbe70
	colorMap.put("LightGoldenrod4", new Color(139, 129, 76)); // 0x8b814c
	colorMap.put("LightGoldenrodYellow", new Color(250, 250, 210)); // 0xfafad2
	colorMap.put("LightGray", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("LightGrey", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("LightPink", new Color(255, 182, 193)); // 0xffb6c1
	colorMap.put("LightPink1", new Color(255, 174, 185)); // 0xffaeb9
	colorMap.put("LightPink2", new Color(238, 162, 173)); // 0xeea2ad
	colorMap.put("LightPink3", new Color(205, 140, 149)); // 0xcd8c95
	colorMap.put("LightPink4", new Color(139, 95, 101)); // 0x8b5f65
	colorMap.put("LightSalmon", new Color(255, 160, 122)); // 0xffa07a
	colorMap.put("LightSalmon1", new Color(255, 160, 122)); // 0xffa07a
	colorMap.put("LightSalmon2", new Color(238, 149, 114)); // 0xee9572
	colorMap.put("LightSalmon3", new Color(205, 129, 98)); // 0xcd8162
	colorMap.put("LightSalmon4", new Color(139, 87, 66)); // 0x8b5742
	colorMap.put("LightSeaGreen", new Color(32, 178, 170)); // 0x20b2aa
	colorMap.put("LightSkyBlue", new Color(135, 206, 250)); // 0x87cefa
	colorMap.put("LightSkyBlue1", new Color(176, 226, 255)); // 0xb0e2ff
	colorMap.put("LightSkyBlue2", new Color(164, 211, 238)); // 0xa4d3ee
	colorMap.put("LightSkyBlue3", new Color(141, 182, 205)); // 0x8db6cd
	colorMap.put("LightSkyBlue4", new Color(96, 123, 139)); // 0x607b8b
	colorMap.put("LightSlateBlue", new Color(132, 112, 255)); // 0x8470ff
	colorMap.put("LightSlateGray", new Color(119, 136, 153)); // 0x778899
	colorMap.put("LightSlateGrey", new Color(119, 136, 153)); // 0x778899
	colorMap.put("LightSteelBlue", new Color(124, 152, 211)); // 0x7c98d3
	colorMap.put("LightSteelBlue1", new Color(202, 225, 255)); // 0xcae1ff
	colorMap.put("LightSteelBlue2", new Color(188, 210, 238)); // 0xbcd2ee
	colorMap.put("LightSteelBlue3", new Color(162, 181, 205)); // 0xa2b5cd
	colorMap.put("LightSteelBlue4", new Color(110, 123, 139)); // 0x6e7b8b
	colorMap.put("LightYellow", new Color(255, 255, 224)); // 0xffffe0
	colorMap.put("LightYellow1", new Color(255, 255, 224)); // 0xffffe0
	colorMap.put("LightYellow2", new Color(238, 238, 209)); // 0xeeeed1
	colorMap.put("LightYellow3", new Color(205, 205, 180)); // 0xcdcdb4
	colorMap.put("LightYellow4", new Color(139, 139, 122)); // 0x8b8b7a
	colorMap.put("LimeGreen", new Color(0, 175, 20)); // 0x00af14
	colorMap.put("Magenta", new Color(255, 0, 255)); // 0xff00ff
	colorMap.put("Maroon", new Color(143, 0, 82)); // 0x8f0052
	colorMap.put("MediumAquamarine", new Color(0, 147, 143)); // 0x00938f
	colorMap.put("MediumBlue", new Color(50, 50, 204)); // 0x3232cc
	colorMap.put("MediumForestGreen", new Color(50, 129, 75)); // 0x32814b
	colorMap.put("MediumGoldenrod", new Color(209, 193, 102)); // 0xd1c166
	colorMap.put("MediumOrchid", new Color(189, 82, 189)); // 0xbd52bd
	colorMap.put("MediumOrchid1", new Color(224, 102, 255)); // 0xe066ff
	colorMap.put("MediumOrchid2", new Color(209, 95, 238)); // 0xd15fee
	colorMap.put("MediumOrchid3", new Color(180, 82, 205)); // 0xb452cd
	colorMap.put("MediumOrchid4", new Color(122, 55, 139)); // 0x7a378b
	colorMap.put("MediumPurple", new Color(147, 112, 219)); // 0x9370db
	colorMap.put("MediumPurple1", new Color(171, 130, 255)); // 0xab82ff
	colorMap.put("MediumPurple2", new Color(159, 121, 238)); // 0x9f79ee
	colorMap.put("MediumPurple3", new Color(137, 104, 205)); // 0x8968cd
	colorMap.put("MediumPurple4", new Color(93, 71, 139)); // 0x5d478b
	colorMap.put("MediumSeaGreen", new Color(52, 119, 102)); // 0x347766
	colorMap.put("MediumSlateBlue", new Color(106, 106, 141)); // 0x6a6a8d
	colorMap.put("MediumSpringGreen", new Color(35, 142, 35)); // 0x238e23
	colorMap.put("MediumTurquoise", new Color(0, 210, 210)); // 0x00d2d2
	colorMap.put("MediumVioletRed", new Color(213, 32, 121)); // 0xd52079
	colorMap.put("MidnightBlue", new Color(47, 47, 100)); // 0x2f2f64
	colorMap.put("MintCream", new Color(245, 255, 250)); // 0xf5fffa
	colorMap.put("MistyRose", new Color(255, 228, 225)); // 0xffe4e1
	colorMap.put("MistyRose1", new Color(255, 228, 225)); // 0xffe4e1
	colorMap.put("MistyRose2", new Color(238, 213, 210)); // 0xeed5d2
	colorMap.put("MistyRose3", new Color(205, 183, 181)); // 0xcdb7b5
	colorMap.put("MistyRose4", new Color(139, 125, 123)); // 0x8b7d7b
	colorMap.put("NavajoWhite", new Color(255, 222, 173)); // 0xffdead
	colorMap.put("NavajoWhite1", new Color(255, 222, 173)); // 0xffdead
	colorMap.put("NavajoWhite2", new Color(238, 207, 161)); // 0xeecfa1
	colorMap.put("NavajoWhite3", new Color(205, 179, 139)); // 0xcdb38b
	colorMap.put("NavajoWhite4", new Color(139, 121, 94)); // 0x8b795e
	colorMap.put("Navy", new Color(35, 35, 117)); // 0x232375
	colorMap.put("NavyBlue", new Color(35, 35, 117)); // 0x232375
	colorMap.put("OldLace", new Color(253, 245, 230)); // 0xfdf5e6
	colorMap.put("OliveDrab", new Color(107, 142, 35)); // 0x6b8e23
	colorMap.put("OliveDrab1", new Color(192, 255, 62)); // 0xc0ff3e
	colorMap.put("OliveDrab2", new Color(179, 238, 58)); // 0xb3ee3a
	colorMap.put("OliveDrab3", new Color(154, 205, 50)); // 0x9acd32
	colorMap.put("OliveDrab4", new Color(105, 139, 34)); // 0x698b22
	colorMap.put("Orange", new Color(255, 135, 0)); // 0xff8700
	colorMap.put("OrangeRed", new Color(255, 69, 0)); // 0xff4500
	colorMap.put("OrangeRed1", new Color(255, 69, 0)); // 0xff4500
	colorMap.put("OrangeRed2", new Color(238, 64, 0)); // 0xee4000
	colorMap.put("OrangeRed3", new Color(205, 55, 0)); // 0xcd3700
	colorMap.put("OrangeRed4", new Color(139, 37, 0)); // 0x8b2500
	colorMap.put("Orchid", new Color(239, 132, 239)); // 0xef84ef
	colorMap.put("PaleGoldenrod", new Color(238, 232, 170)); // 0xeee8aa
	colorMap.put("PaleGreen", new Color(115, 222, 120)); // 0x73de78
	colorMap.put("PaleGreen1", new Color(154, 255, 154)); // 0x9aff9a
	colorMap.put("PaleGreen2", new Color(144, 238, 144)); // 0x90ee90
	colorMap.put("PaleGreen3", new Color(124, 205, 124)); // 0x7ccd7c
	colorMap.put("PaleGreen4", new Color(84, 139, 84)); // 0x548b54
	colorMap.put("PaleTurquoise", new Color(175, 238, 238)); // 0xafeeee
	colorMap.put("PaleTurquoise1", new Color(187, 255, 255)); // 0xbbffff
	colorMap.put("PaleTurquoise2", new Color(174, 238, 238)); // 0xaeeeee
	colorMap.put("PaleTurquoise3", new Color(150, 205, 205)); // 0x96cdcd
	colorMap.put("PaleTurquoise4", new Color(102, 139, 139)); // 0x668b8b
	colorMap.put("PaleVioletRed", new Color(219, 112, 147)); // 0xdb7093
	colorMap.put("PaleVioletRed1", new Color(255, 130, 171)); // 0xff82ab
	colorMap.put("PaleVioletRed2", new Color(238, 121, 159)); // 0xee799f
	colorMap.put("PaleVioletRed3", new Color(205, 104, 137)); // 0xcd6889
	colorMap.put("PaleVioletRed4", new Color(139, 71, 93)); // 0x8b475d
	colorMap.put("PapayaWhip", new Color(255, 239, 213)); // 0xffefd5
	colorMap.put("PeachPuff", new Color(255, 218, 185)); // 0xffdab9
	colorMap.put("PeachPuff1", new Color(255, 218, 185)); // 0xffdab9
	colorMap.put("PeachPuff2", new Color(238, 203, 173)); // 0xeecbad
	colorMap.put("PeachPuff3", new Color(205, 175, 149)); // 0xcdaf95
	colorMap.put("PeachPuff4", new Color(139, 119, 101)); // 0x8b7765
	colorMap.put("Pink", new Color(255, 181, 197)); // 0xffb5c5
	colorMap.put("Plum", new Color(197, 72, 155)); // 0xc5489b
	colorMap.put("PowderBlue", new Color(176, 224, 230)); // 0xb0e0e6
	colorMap.put("Red", new Color(255, 0, 0)); // 0xff0000
	colorMap.put("RosyBrown", new Color(188, 143, 143)); // 0xbc8f8f
	colorMap.put("RosyBrown1", new Color(255, 193, 193)); // 0xffc1c1
	colorMap.put("RosyBrown2", new Color(238, 180, 180)); // 0xeeb4b4
	colorMap.put("RosyBrown3", new Color(205, 155, 155)); // 0xcd9b9b
	colorMap.put("RosyBrown4", new Color(139, 105, 105)); // 0x8b6969
	colorMap.put("RoyalBlue", new Color(65, 105, 225)); // 0x4169e1
	colorMap.put("RoyalBlue1", new Color(72, 118, 255)); // 0x4876ff
	colorMap.put("RoyalBlue2", new Color(67, 110, 238)); // 0x436eee
	colorMap.put("RoyalBlue3", new Color(58, 95, 205)); // 0x3a5fcd
	colorMap.put("RoyalBlue4", new Color(39, 64, 139)); // 0x27408b
	colorMap.put("SaddleBrown", new Color(139, 69, 19)); // 0x8b4513
	colorMap.put("Salmon", new Color(233, 150, 122)); // 0xe9967a
	colorMap.put("SandyBrown", new Color(244, 164, 96)); // 0xf4a460
	colorMap.put("SeaGreen", new Color(82, 149, 132)); // 0x529584
	colorMap.put("SeaGreen1", new Color(84, 255, 159)); // 0x54ff9f
	colorMap.put("SeaGreen2", new Color(78, 238, 148)); // 0x4eee94
	colorMap.put("SeaGreen3", new Color(67, 205, 128)); // 0x43cd80
	colorMap.put("SeaGreen4", new Color(46, 139, 87)); // 0x2e8b57
	colorMap.put("Sienna", new Color(150, 82, 45)); // 0x96522d
	colorMap.put("SkyBlue", new Color(114, 159, 255)); // 0x729fff
	colorMap.put("SkyBlue1", new Color(135, 206, 255)); // 0x87ceff
	colorMap.put("SkyBlue2", new Color(126, 192, 238)); // 0x7ec0ee
	colorMap.put("SkyBlue3", new Color(108, 166, 205)); // 0x6ca6cd
	colorMap.put("SkyBlue4", new Color(74, 112, 139)); // 0x4a708b
	colorMap.put("SlateBlue", new Color(126, 136, 171)); // 0x7e88ab
	colorMap.put("SlateBlue1", new Color(131, 111, 255)); // 0x836fff
	colorMap.put("SlateBlue2", new Color(122, 103, 238)); // 0x7a67ee
	colorMap.put("SlateBlue3", new Color(105, 89, 205)); // 0x6959cd
	colorMap.put("SlateBlue4", new Color(71, 60, 139)); // 0x473c8b
	colorMap.put("SlateGray", new Color(112, 128, 144)); // 0x708090
	colorMap.put("SlateGray1", new Color(198, 226, 255)); // 0xc6e2ff
	colorMap.put("SlateGray2", new Color(185, 211, 238)); // 0xb9d3ee
	colorMap.put("SlateGray3", new Color(159, 182, 205)); // 0x9fb6cd
	colorMap.put("SlateGray4", new Color(108, 123, 139)); // 0x6c7b8b
	colorMap.put("SlateGrey", new Color(112, 128, 144)); // 0x708090
	colorMap.put("SpringGreen", new Color(65, 172, 65)); // 0x41ac41
	colorMap.put("SpringGreen1", new Color(0, 255, 127)); // 0x00ff7f
	colorMap.put("SpringGreen2", new Color(0, 238, 118)); // 0x00ee76
	colorMap.put("SpringGreen3", new Color(0, 205, 102)); // 0x00cd66
	colorMap.put("SpringGreen4", new Color(0, 139, 69)); // 0x008b45
	colorMap.put("SteelBlue", new Color(84, 112, 170)); // 0x5470aa
	colorMap.put("SteelBlue1", new Color(99, 184, 255)); // 0x63b8ff
	colorMap.put("SteelBlue2", new Color(92, 172, 238)); // 0x5cacee
	colorMap.put("SteelBlue3", new Color(79, 148, 205)); // 0x4f94cd
	colorMap.put("SteelBlue4", new Color(54, 100, 139)); // 0x36648b
	colorMap.put("Tan", new Color(222, 184, 135)); // 0xdeb887
	colorMap.put("Thistle", new Color(216, 191, 216)); // 0xd8bfd8
	colorMap.put("Transparent", new Color(0, 0, 1)); // 0x000001
	colorMap.put("Turquoise", new Color(25, 204, 223)); // 0x19ccdf
	colorMap.put("Violet", new Color(156, 62, 206)); // 0x9c3ece
	colorMap.put("VioletRed", new Color(243, 62, 150)); // 0xf33e96
	colorMap.put("VioletRed1", new Color(255, 62, 150)); // 0xff3e96
	colorMap.put("VioletRed2", new Color(238, 58, 140)); // 0xee3a8c
	colorMap.put("VioletRed3", new Color(205, 50, 120)); // 0xcd3278
	colorMap.put("VioletRed4", new Color(139, 34, 82)); // 0x8b2252
	colorMap.put("Wheat", new Color(245, 222, 179)); // 0xf5deb3
	colorMap.put("White", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("WhiteSmoke", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("Yellow", new Color(255, 255, 0)); // 0xffff00
	colorMap.put("YellowGreen", new Color(50, 216, 56)); // 0x32d838
	colorMap.put("alice blue", new Color(240, 248, 255)); // 0xf0f8ff
	colorMap.put("antique white", new Color(250, 235, 215)); // 0xfaebd7
	colorMap.put("aquamarine", new Color(50, 191, 193)); // 0x32bfc1
	colorMap.put("aquamarine1", new Color(127, 255, 212)); // 0x7fffd4
	colorMap.put("aquamarine2", new Color(118, 238, 198)); // 0x76eec6
	colorMap.put("aquamarine3", new Color(102, 205, 170)); // 0x66cdaa
	colorMap.put("aquamarine4", new Color(69, 139, 116)); // 0x458b74
	colorMap.put("azure", new Color(240, 255, 255)); // 0xf0ffff
	colorMap.put("azure1", new Color(240, 255, 255)); // 0xf0ffff
	colorMap.put("azure2", new Color(224, 238, 238)); // 0xe0eeee
	colorMap.put("azure3", new Color(193, 205, 205)); // 0xc1cdcd
	colorMap.put("azure4", new Color(131, 139, 139)); // 0x838b8b
	colorMap.put("beige", new Color(245, 245, 220)); // 0xf5f5dc
	colorMap.put("bisque", new Color(255, 228, 196)); // 0xffe4c4
	colorMap.put("bisque1", new Color(255, 228, 196)); // 0xffe4c4
	colorMap.put("bisque2", new Color(238, 213, 183)); // 0xeed5b7
	colorMap.put("bisque3", new Color(205, 183, 158)); // 0xcdb79e
	colorMap.put("bisque4", new Color(139, 125, 107)); // 0x8b7d6b
	colorMap.put("black", new Color(0, 0, 0)); // 0x000000
	colorMap.put("blanched almond", new Color(255, 235, 205)); // 0xffebcd
	colorMap.put("blue", new Color(0, 0, 255)); // 0x0000ff
	colorMap.put("blue violet", new Color(138, 43, 226)); // 0x8a2be2
	colorMap.put("blue1", new Color(0, 0, 255)); // 0x0000ff
	colorMap.put("blue2", new Color(0, 0, 238)); // 0x0000ee
	colorMap.put("blue3", new Color(0, 0, 205)); // 0x0000cd
	colorMap.put("blue4", new Color(0, 0, 139)); // 0x00008b
	colorMap.put("brickred", new Color(128, 0, 0)); // 0x800000 CHxx
	colorMap.put("brown", new Color(165, 42, 42)); // 0xa52a2a
	colorMap.put("brown1", new Color(255, 64, 64)); // 0xff4040
	colorMap.put("brown2", new Color(238, 59, 59)); // 0xee3b3b
	colorMap.put("brown3", new Color(205, 51, 51)); // 0xcd3333
	colorMap.put("brown4", new Color(139, 35, 35)); // 0x8b2323
	colorMap.put("burlywood", new Color(222, 184, 135)); // 0xdeb887
	colorMap.put("burlywood1", new Color(255, 211, 155)); // 0xffd39b
	colorMap.put("burlywood2", new Color(238, 197, 145)); // 0xeec591
	colorMap.put("burlywood3", new Color(205, 170, 125)); // 0xcdaa7d
	colorMap.put("burlywood4", new Color(139, 115, 85)); // 0x8b7355
	colorMap.put("cadet blue", new Color(95, 146, 158)); // 0x5f929e
	colorMap.put("chartreuse", new Color(127, 255, 0)); // 0x7fff00
	colorMap.put("chartreuse1", new Color(127, 255, 0)); // 0x7fff00
	colorMap.put("chartreuse2", new Color(118, 238, 0)); // 0x76ee00
	colorMap.put("chartreuse3", new Color(102, 205, 0)); // 0x66cd00
	colorMap.put("chartreuse4", new Color(69, 139, 0)); // 0x458b00
	colorMap.put("chocolate", new Color(210, 105, 30)); // 0xd2691e
	colorMap.put("chocolate1", new Color(255, 127, 36)); // 0xff7f24
	colorMap.put("chocolate2", new Color(238, 118, 33)); // 0xee7621
	colorMap.put("chocolate3", new Color(205, 102, 29)); // 0xcd661d
	colorMap.put("chocolate4", new Color(139, 69, 19)); // 0x8b4513
	colorMap.put("coral", new Color(255, 114, 86)); // 0xff7256
	colorMap.put("coral1", new Color(255, 114, 86)); // 0xff7256
	colorMap.put("coral2", new Color(238, 106, 80)); // 0xee6a50
	colorMap.put("coral3", new Color(205, 91, 69)); // 0xcd5b45
	colorMap.put("coral4", new Color(139, 62, 47)); // 0x8b3e2f
	colorMap.put("cornflower blue", new Color(34, 34, 152)); // 0x222298
	colorMap.put("cornsilk", new Color(255, 248, 220)); // 0xfff8dc
	colorMap.put("cornsilk1", new Color(255, 248, 220)); // 0xfff8dc
	colorMap.put("cornsilk2", new Color(238, 232, 205)); // 0xeee8cd
	colorMap.put("cornsilk3", new Color(205, 200, 177)); // 0xcdc8b1
	colorMap.put("cornsilk4", new Color(139, 136, 120)); // 0x8b8878
	colorMap.put("cyan", new Color(0, 255, 255)); // 0x00ffff
	colorMap.put("cyan1", new Color(0, 255, 255)); // 0x00ffff
	colorMap.put("cyan2", new Color(0, 238, 238)); // 0x00eeee
	colorMap.put("cyan3", new Color(0, 205, 205)); // 0x00cdcd
	colorMap.put("cyan4", new Color(0, 139, 139)); // 0x008b8b
	colorMap.put("dark goldenrod", new Color(184, 134, 11)); // 0xb8860b
	colorMap.put("dark green", new Color(0, 86, 45)); // 0x00562d
	colorMap.put("dark khaki", new Color(189, 183, 107)); // 0xbdb76b
	colorMap.put("dark olive green", new Color(85, 86, 47)); // 0x55562f
	colorMap.put("dark orange", new Color(255, 140, 0)); // 0xff8c00
	colorMap.put("dark orchid", new Color(139, 32, 139)); // 0x8b208b
	colorMap.put("dark salmon", new Color(233, 150, 122)); // 0xe9967a
	colorMap.put("dark sea green", new Color(143, 188, 143)); // 0x8fbc8f
	colorMap.put("dark slate blue", new Color(56, 75, 102)); // 0x384b66
	colorMap.put("dark slate gray", new Color(47, 79, 79)); // 0x2f4f4f
	colorMap.put("dark slate grey", new Color(47, 79, 79)); // 0x2f4f4f
	colorMap.put("dark turquoise", new Color(0, 166, 166)); // 0x00a6a6
	colorMap.put("dark violet", new Color(148, 0, 211)); // 0x9400d3
	colorMap.put("deep pink", new Color(255, 20, 147)); // 0xff1493
	colorMap.put("deep sky blue", new Color(0, 191, 255)); // 0x00bfff
	colorMap.put("dim gray", new Color(84, 84, 84)); // 0x545454
	colorMap.put("dim grey", new Color(84, 84, 84)); // 0x545454
	colorMap.put("dodger blue", new Color(30, 144, 255)); // 0x1e90ff
	colorMap.put("firebrick", new Color(142, 35, 35)); // 0x8e2323
	colorMap.put("firebrick1", new Color(255, 48, 48)); // 0xff3030
	colorMap.put("firebrick2", new Color(238, 44, 44)); // 0xee2c2c
	colorMap.put("firebrick3", new Color(205, 38, 38)); // 0xcd2626
	colorMap.put("firebrick4", new Color(139, 26, 26)); // 0x8b1a1a
	colorMap.put("floral white", new Color(255, 250, 240)); // 0xfffaf0
	colorMap.put("forest green", new Color(80, 159, 105)); // 0x509f69
	colorMap.put("gainsboro", new Color(220, 220, 220)); // 0xdcdcdc
	colorMap.put("ghost white", new Color(248, 248, 255)); // 0xf8f8ff
	colorMap.put("gold", new Color(218, 170, 0)); // 0xdaaa00
	colorMap.put("gold1", new Color(255, 215, 0)); // 0xffd700
	colorMap.put("gold2", new Color(238, 201, 0)); // 0xeec900
	colorMap.put("gold3", new Color(205, 173, 0)); // 0xcdad00
	colorMap.put("gold4", new Color(139, 117, 0)); // 0x8b7500
	colorMap.put("goldenrod", new Color(239, 223, 132)); // 0xefdf84
	colorMap.put("goldenrod1", new Color(255, 193, 37)); // 0xffc125
	colorMap.put("goldenrod2", new Color(238, 180, 34)); // 0xeeb422
	colorMap.put("goldenrod3", new Color(205, 155, 29)); // 0xcd9b1d
	colorMap.put("goldenrod4", new Color(139, 105, 20)); // 0x8b6914
	colorMap.put("gray", new Color(126, 126, 126)); // 0x7e7e7e
	colorMap.put("gray0", new Color(0, 0, 0)); // 0x000000
	colorMap.put("gray1", new Color(3, 3, 3)); // 0x030303
	colorMap.put("gray10", new Color(26, 26, 26)); // 0x1a1a1a
	colorMap.put("gray100", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("gray11", new Color(28, 28, 28)); // 0x1c1c1c
	colorMap.put("gray12", new Color(31, 31, 31)); // 0x1f1f1f
	colorMap.put("gray13", new Color(33, 33, 33)); // 0x212121
	colorMap.put("gray14", new Color(36, 36, 36)); // 0x242424
	colorMap.put("gray15", new Color(38, 38, 38)); // 0x262626
	colorMap.put("gray16", new Color(41, 41, 41)); // 0x292929
	colorMap.put("gray17", new Color(43, 43, 43)); // 0x2b2b2b
	colorMap.put("gray18", new Color(46, 46, 46)); // 0x2e2e2e
	colorMap.put("gray19", new Color(48, 48, 48)); // 0x303030
	colorMap.put("gray2", new Color(5, 5, 5)); // 0x050505
	colorMap.put("gray20", new Color(51, 51, 51)); // 0x333333
	colorMap.put("gray21", new Color(54, 54, 54)); // 0x363636
	colorMap.put("gray22", new Color(56, 56, 56)); // 0x383838
	colorMap.put("gray23", new Color(59, 59, 59)); // 0x3b3b3b
	colorMap.put("gray24", new Color(61, 61, 61)); // 0x3d3d3d
	colorMap.put("gray25", new Color(64, 64, 64)); // 0x404040
	colorMap.put("gray26", new Color(66, 66, 66)); // 0x424242
	colorMap.put("gray27", new Color(69, 69, 69)); // 0x454545
	colorMap.put("gray28", new Color(71, 71, 71)); // 0x474747
	colorMap.put("gray29", new Color(74, 74, 74)); // 0x4a4a4a
	colorMap.put("gray3", new Color(8, 8, 8)); // 0x080808
	colorMap.put("gray30", new Color(77, 77, 77)); // 0x4d4d4d
	colorMap.put("gray31", new Color(79, 79, 79)); // 0x4f4f4f
	colorMap.put("gray32", new Color(82, 82, 82)); // 0x525252
	colorMap.put("gray33", new Color(84, 84, 84)); // 0x545454
	colorMap.put("gray34", new Color(87, 87, 87)); // 0x575757
	colorMap.put("gray35", new Color(89, 89, 89)); // 0x595959
	colorMap.put("gray36", new Color(92, 92, 92)); // 0x5c5c5c
	colorMap.put("gray37", new Color(94, 94, 94)); // 0x5e5e5e
	colorMap.put("gray38", new Color(97, 97, 97)); // 0x616161
	colorMap.put("gray39", new Color(99, 99, 99)); // 0x636363
	colorMap.put("gray4", new Color(10, 10, 10)); // 0x0a0a0a
	colorMap.put("gray40", new Color(102, 102, 102)); // 0x666666
	colorMap.put("gray41", new Color(105, 105, 105)); // 0x696969
	colorMap.put("gray42", new Color(107, 107, 107)); // 0x6b6b6b
	colorMap.put("gray43", new Color(110, 110, 110)); // 0x6e6e6e
	colorMap.put("gray44", new Color(112, 112, 112)); // 0x707070
	colorMap.put("gray45", new Color(115, 115, 115)); // 0x737373
	colorMap.put("gray46", new Color(117, 117, 117)); // 0x757575
	colorMap.put("gray47", new Color(120, 120, 120)); // 0x787878
	colorMap.put("gray48", new Color(122, 122, 122)); // 0x7a7a7a
	colorMap.put("gray49", new Color(125, 125, 125)); // 0x7d7d7d
	colorMap.put("gray5", new Color(13, 13, 13)); // 0x0d0d0d
	colorMap.put("gray50", new Color(127, 127, 127)); // 0x7f7f7f
	colorMap.put("gray51", new Color(130, 130, 130)); // 0x828282
	colorMap.put("gray52", new Color(133, 133, 133)); // 0x858585
	colorMap.put("gray53", new Color(135, 135, 135)); // 0x878787
	colorMap.put("gray54", new Color(138, 138, 138)); // 0x8a8a8a
	colorMap.put("gray55", new Color(140, 140, 140)); // 0x8c8c8c
	colorMap.put("gray56", new Color(143, 143, 143)); // 0x8f8f8f
	colorMap.put("gray57", new Color(145, 145, 145)); // 0x919191
	colorMap.put("gray58", new Color(148, 148, 148)); // 0x949494
	colorMap.put("gray59", new Color(150, 150, 150)); // 0x969696
	colorMap.put("gray6", new Color(15, 15, 15)); // 0x0f0f0f
	colorMap.put("gray60", new Color(153, 153, 153)); // 0x999999
	colorMap.put("gray61", new Color(156, 156, 156)); // 0x9c9c9c
	colorMap.put("gray62", new Color(158, 158, 158)); // 0x9e9e9e
	colorMap.put("gray63", new Color(161, 161, 161)); // 0xa1a1a1
	colorMap.put("gray64", new Color(163, 163, 163)); // 0xa3a3a3
	colorMap.put("gray65", new Color(166, 166, 166)); // 0xa6a6a6
	colorMap.put("gray66", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("gray67", new Color(171, 171, 171)); // 0xababab
	colorMap.put("gray68", new Color(173, 173, 173)); // 0xadadad
	colorMap.put("gray69", new Color(176, 176, 176)); // 0xb0b0b0
	colorMap.put("gray7", new Color(18, 18, 18)); // 0x121212
	colorMap.put("gray70", new Color(179, 179, 179)); // 0xb3b3b3
	colorMap.put("gray71", new Color(181, 181, 181)); // 0xb5b5b5
	colorMap.put("gray72", new Color(184, 184, 184)); // 0xb8b8b8
	colorMap.put("gray73", new Color(186, 186, 186)); // 0xbababa
	colorMap.put("gray74", new Color(189, 189, 189)); // 0xbdbdbd
	colorMap.put("gray75", new Color(191, 191, 191)); // 0xbfbfbf
	colorMap.put("gray76", new Color(194, 194, 194)); // 0xc2c2c2
	colorMap.put("gray77", new Color(196, 196, 196)); // 0xc4c4c4
	colorMap.put("gray78", new Color(199, 199, 199)); // 0xc7c7c7
	colorMap.put("gray79", new Color(201, 201, 201)); // 0xc9c9c9
	colorMap.put("gray8", new Color(20, 20, 20)); // 0x141414
	colorMap.put("gray80", new Color(204, 204, 204)); // 0xcccccc
	colorMap.put("gray81", new Color(207, 207, 207)); // 0xcfcfcf
	colorMap.put("gray82", new Color(209, 209, 209)); // 0xd1d1d1
	colorMap.put("gray83", new Color(212, 212, 212)); // 0xd4d4d4
	colorMap.put("gray84", new Color(214, 214, 214)); // 0xd6d6d6
	colorMap.put("gray85", new Color(217, 217, 217)); // 0xd9d9d9
	colorMap.put("gray86", new Color(219, 219, 219)); // 0xdbdbdb
	colorMap.put("gray87", new Color(222, 222, 222)); // 0xdedede
	colorMap.put("gray88", new Color(224, 224, 224)); // 0xe0e0e0
	colorMap.put("gray89", new Color(227, 227, 227)); // 0xe3e3e3
	colorMap.put("gray9", new Color(23, 23, 23)); // 0x171717
	colorMap.put("gray90", new Color(229, 229, 229)); // 0xe5e5e5
	colorMap.put("gray91", new Color(232, 232, 232)); // 0xe8e8e8
	colorMap.put("gray92", new Color(235, 235, 235)); // 0xebebeb
	colorMap.put("gray93", new Color(237, 237, 237)); // 0xededed
	colorMap.put("gray94", new Color(240, 240, 240)); // 0xf0f0f0
	colorMap.put("gray95", new Color(242, 242, 242)); // 0xf2f2f2
	colorMap.put("gray96", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("gray97", new Color(247, 247, 247)); // 0xf7f7f7
	colorMap.put("gray98", new Color(250, 250, 250)); // 0xfafafa
	colorMap.put("gray99", new Color(252, 252, 252)); // 0xfcfcfc
	colorMap.put("green", new Color(0, 255, 0)); // 0x00ff00
	colorMap.put("green yellow", new Color(173, 255, 47)); // 0xadff2f
	colorMap.put("green1", new Color(0, 255, 0)); // 0x00ff00
	colorMap.put("green2", new Color(0, 238, 0)); // 0x00ee00
	colorMap.put("green3", new Color(0, 205, 0)); // 0x00cd00
	colorMap.put("green4", new Color(0, 139, 0)); // 0x008b00
	colorMap.put("grey", new Color(126, 126, 126)); // 0x7e7e7e
	colorMap.put("grey0", new Color(0, 0, 0)); // 0x000000
	colorMap.put("grey1", new Color(3, 3, 3)); // 0x030303
	colorMap.put("grey10", new Color(26, 26, 26)); // 0x1a1a1a
	colorMap.put("grey100", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("grey11", new Color(28, 28, 28)); // 0x1c1c1c
	colorMap.put("grey12", new Color(31, 31, 31)); // 0x1f1f1f
	colorMap.put("grey13", new Color(33, 33, 33)); // 0x212121
	colorMap.put("grey14", new Color(36, 36, 36)); // 0x242424
	colorMap.put("grey15", new Color(38, 38, 38)); // 0x262626
	colorMap.put("grey16", new Color(41, 41, 41)); // 0x292929
	colorMap.put("grey17", new Color(43, 43, 43)); // 0x2b2b2b
	colorMap.put("grey18", new Color(46, 46, 46)); // 0x2e2e2e
	colorMap.put("grey19", new Color(48, 48, 48)); // 0x303030
	colorMap.put("grey2", new Color(5, 5, 5)); // 0x050505
	colorMap.put("grey20", new Color(51, 51, 51)); // 0x333333
	colorMap.put("grey21", new Color(54, 54, 54)); // 0x363636
	colorMap.put("grey22", new Color(56, 56, 56)); // 0x383838
	colorMap.put("grey23", new Color(59, 59, 59)); // 0x3b3b3b
	colorMap.put("grey24", new Color(61, 61, 61)); // 0x3d3d3d
	colorMap.put("grey25", new Color(64, 64, 64)); // 0x404040
	colorMap.put("grey26", new Color(66, 66, 66)); // 0x424242
	colorMap.put("grey27", new Color(69, 69, 69)); // 0x454545
	colorMap.put("grey28", new Color(71, 71, 71)); // 0x474747
	colorMap.put("grey29", new Color(74, 74, 74)); // 0x4a4a4a
	colorMap.put("grey3", new Color(8, 8, 8)); // 0x080808
	colorMap.put("grey30", new Color(77, 77, 77)); // 0x4d4d4d
	colorMap.put("grey31", new Color(79, 79, 79)); // 0x4f4f4f
	colorMap.put("grey32", new Color(82, 82, 82)); // 0x525252
	colorMap.put("grey33", new Color(84, 84, 84)); // 0x545454
	colorMap.put("grey34", new Color(87, 87, 87)); // 0x575757
	colorMap.put("grey35", new Color(89, 89, 89)); // 0x595959
	colorMap.put("grey36", new Color(92, 92, 92)); // 0x5c5c5c
	colorMap.put("grey37", new Color(94, 94, 94)); // 0x5e5e5e
	colorMap.put("grey38", new Color(97, 97, 97)); // 0x616161
	colorMap.put("grey39", new Color(99, 99, 99)); // 0x636363
	colorMap.put("grey4", new Color(10, 10, 10)); // 0x0a0a0a
	colorMap.put("grey40", new Color(102, 102, 102)); // 0x666666
	colorMap.put("grey41", new Color(105, 105, 105)); // 0x696969
	colorMap.put("grey42", new Color(107, 107, 107)); // 0x6b6b6b
	colorMap.put("grey43", new Color(110, 110, 110)); // 0x6e6e6e
	colorMap.put("grey44", new Color(112, 112, 112)); // 0x707070
	colorMap.put("grey45", new Color(115, 115, 115)); // 0x737373
	colorMap.put("grey46", new Color(117, 117, 117)); // 0x757575
	colorMap.put("grey47", new Color(120, 120, 120)); // 0x787878
	colorMap.put("grey48", new Color(122, 122, 122)); // 0x7a7a7a
	colorMap.put("grey49", new Color(125, 125, 125)); // 0x7d7d7d
	colorMap.put("grey5", new Color(13, 13, 13)); // 0x0d0d0d
	colorMap.put("grey50", new Color(127, 127, 127)); // 0x7f7f7f
	colorMap.put("grey51", new Color(130, 130, 130)); // 0x828282
	colorMap.put("grey52", new Color(133, 133, 133)); // 0x858585
	colorMap.put("grey53", new Color(135, 135, 135)); // 0x878787
	colorMap.put("grey54", new Color(138, 138, 138)); // 0x8a8a8a
	colorMap.put("grey55", new Color(140, 140, 140)); // 0x8c8c8c
	colorMap.put("grey56", new Color(143, 143, 143)); // 0x8f8f8f
	colorMap.put("grey57", new Color(145, 145, 145)); // 0x919191
	colorMap.put("grey58", new Color(148, 148, 148)); // 0x949494
	colorMap.put("grey59", new Color(150, 150, 150)); // 0x969696
	colorMap.put("grey6", new Color(15, 15, 15)); // 0x0f0f0f
	colorMap.put("grey60", new Color(153, 153, 153)); // 0x999999
	colorMap.put("grey61", new Color(156, 156, 156)); // 0x9c9c9c
	colorMap.put("grey62", new Color(158, 158, 158)); // 0x9e9e9e
	colorMap.put("grey63", new Color(161, 161, 161)); // 0xa1a1a1
	colorMap.put("grey64", new Color(163, 163, 163)); // 0xa3a3a3
	colorMap.put("grey65", new Color(166, 166, 166)); // 0xa6a6a6
	colorMap.put("grey66", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("grey67", new Color(171, 171, 171)); // 0xababab
	colorMap.put("grey68", new Color(173, 173, 173)); // 0xadadad
	colorMap.put("grey69", new Color(176, 176, 176)); // 0xb0b0b0
	colorMap.put("grey7", new Color(18, 18, 18)); // 0x121212
	colorMap.put("grey70", new Color(179, 179, 179)); // 0xb3b3b3
	colorMap.put("grey71", new Color(181, 181, 181)); // 0xb5b5b5
	colorMap.put("grey72", new Color(184, 184, 184)); // 0xb8b8b8
	colorMap.put("grey73", new Color(186, 186, 186)); // 0xbababa
	colorMap.put("grey74", new Color(189, 189, 189)); // 0xbdbdbd
	colorMap.put("grey75", new Color(191, 191, 191)); // 0xbfbfbf
	colorMap.put("grey76", new Color(194, 194, 194)); // 0xc2c2c2
	colorMap.put("grey77", new Color(196, 196, 196)); // 0xc4c4c4
	colorMap.put("grey78", new Color(199, 199, 199)); // 0xc7c7c7
	colorMap.put("grey79", new Color(201, 201, 201)); // 0xc9c9c9
	colorMap.put("grey8", new Color(20, 20, 20)); // 0x141414
	colorMap.put("grey80", new Color(204, 204, 204)); // 0xcccccc
	colorMap.put("grey81", new Color(207, 207, 207)); // 0xcfcfcf
	colorMap.put("grey82", new Color(209, 209, 209)); // 0xd1d1d1
	colorMap.put("grey83", new Color(212, 212, 212)); // 0xd4d4d4
	colorMap.put("grey84", new Color(214, 214, 214)); // 0xd6d6d6
	colorMap.put("grey85", new Color(217, 217, 217)); // 0xd9d9d9
	colorMap.put("grey86", new Color(219, 219, 219)); // 0xdbdbdb
	colorMap.put("grey87", new Color(222, 222, 222)); // 0xdedede
	colorMap.put("grey88", new Color(224, 224, 224)); // 0xe0e0e0
	colorMap.put("grey89", new Color(227, 227, 227)); // 0xe3e3e3
	colorMap.put("grey9", new Color(23, 23, 23)); // 0x171717
	colorMap.put("grey90", new Color(229, 229, 229)); // 0xe5e5e5
	colorMap.put("grey91", new Color(232, 232, 232)); // 0xe8e8e8
	colorMap.put("grey92", new Color(235, 235, 235)); // 0xebebeb
	colorMap.put("grey93", new Color(237, 237, 237)); // 0xededed
	colorMap.put("grey94", new Color(240, 240, 240)); // 0xf0f0f0
	colorMap.put("grey95", new Color(242, 242, 242)); // 0xf2f2f2
	colorMap.put("grey96", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("grey97", new Color(247, 247, 247)); // 0xf7f7f7
	colorMap.put("grey98", new Color(250, 250, 250)); // 0xfafafa
	colorMap.put("grey99", new Color(252, 252, 252)); // 0xfcfcfc
	colorMap.put("honeydew", new Color(240, 255, 240)); // 0xf0fff0
	colorMap.put("honeydew1", new Color(240, 255, 240)); // 0xf0fff0
	colorMap.put("honeydew2", new Color(224, 238, 224)); // 0xe0eee0
	colorMap.put("honeydew3", new Color(193, 205, 193)); // 0xc1cdc1
	colorMap.put("honeydew4", new Color(131, 139, 131)); // 0x838b83
	colorMap.put("hot pink", new Color(255, 105, 180)); // 0xff69b4
	colorMap.put("indian red", new Color(107, 57, 57)); // 0x6b3939
	colorMap.put("ivory", new Color(255, 255, 240)); // 0xfffff0
	colorMap.put("ivory1", new Color(255, 255, 240)); // 0xfffff0
	colorMap.put("ivory2", new Color(238, 238, 224)); // 0xeeeee0
	colorMap.put("ivory3", new Color(205, 205, 193)); // 0xcdcdc1
	colorMap.put("ivory4", new Color(139, 139, 131)); // 0x8b8b83
	colorMap.put("khaki", new Color(179, 179, 126)); // 0xb3b37e
	colorMap.put("khaki1", new Color(255, 246, 143)); // 0xfff68f
	colorMap.put("khaki2", new Color(238, 230, 133)); // 0xeee685
	colorMap.put("khaki3", new Color(205, 198, 115)); // 0xcdc673
	colorMap.put("khaki4", new Color(139, 134, 78)); // 0x8b864e
	colorMap.put("lavender", new Color(230, 230, 250)); // 0xe6e6fa
	colorMap.put("lavender blush", new Color(255, 240, 245)); // 0xfff0f5
	colorMap.put("lawn green", new Color(124, 252, 0)); // 0x7cfc00
	colorMap.put("lemon chiffon", new Color(255, 250, 205)); // 0xfffacd
	colorMap.put("light blue", new Color(176, 226, 255)); // 0xb0e2ff
	colorMap.put("light coral", new Color(240, 128, 128)); // 0xf08080
	colorMap.put("light cyan", new Color(224, 255, 255)); // 0xe0ffff
	colorMap.put("light goldenrod", new Color(238, 221, 130)); // 0xeedd82
	colorMap.put("light goldenrod yellow", new Color(250, 250, 210)); // 0xfafad2
	colorMap.put("light gray", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("light grey", new Color(168, 168, 168)); // 0xa8a8a8
	colorMap.put("light pink", new Color(255, 182, 193)); // 0xffb6c1
	colorMap.put("light salmon", new Color(255, 160, 122)); // 0xffa07a
	colorMap.put("light sea green", new Color(32, 178, 170)); // 0x20b2aa
	colorMap.put("light sky blue", new Color(135, 206, 250)); // 0x87cefa
	colorMap.put("light slate blue", new Color(132, 112, 255)); // 0x8470ff
	colorMap.put("light slate gray", new Color(119, 136, 153)); // 0x778899
	colorMap.put("light slate grey", new Color(119, 136, 153)); // 0x778899
	colorMap.put("light steel blue", new Color(124, 152, 211)); // 0x7c98d3
	colorMap.put("light yellow", new Color(255, 255, 224)); // 0xffffe0
	colorMap.put("lime green", new Color(0, 175, 20)); // 0x00af14
	colorMap.put("linen", new Color(250, 240, 230)); // 0xfaf0e6
	colorMap.put("magenta", new Color(255, 0, 255)); // 0xff00ff
	colorMap.put("magenta1", new Color(255, 0, 255)); // 0xff00ff
	colorMap.put("magenta2", new Color(238, 0, 238)); // 0xee00ee
	colorMap.put("magenta3", new Color(205, 0, 205)); // 0xcd00cd
	colorMap.put("magenta4", new Color(139, 0, 139)); // 0x8b008b
	colorMap.put("maroon", new Color(143, 0, 82)); // 0x8f0052
	colorMap.put("maroon1", new Color(255, 52, 179)); // 0xff34b3
	colorMap.put("maroon2", new Color(238, 48, 167)); // 0xee30a7
	colorMap.put("maroon3", new Color(205, 41, 144)); // 0xcd2990
	colorMap.put("maroon4", new Color(139, 28, 98)); // 0x8b1c62
	colorMap.put("medium aquamarine", new Color(0, 147, 143)); // 0x00938f
	colorMap.put("medium blue", new Color(50, 50, 204)); // 0x3232cc
	colorMap.put("medium forest green", new Color(50, 129, 75)); // 0x32814b
	colorMap.put("medium goldenrod", new Color(209, 193, 102)); // 0xd1c166
	colorMap.put("medium orchid", new Color(189, 82, 189)); // 0xbd52bd
	colorMap.put("medium purple", new Color(147, 112, 219)); // 0x9370db
	colorMap.put("medium sea green", new Color(52, 119, 102)); // 0x347766
	colorMap.put("medium slate blue", new Color(106, 106, 141)); // 0x6a6a8d
	colorMap.put("medium spring green", new Color(35, 142, 35)); // 0x238e23
	colorMap.put("medium turquoise", new Color(0, 210, 210)); // 0x00d2d2
	colorMap.put("medium violet red", new Color(213, 32, 121)); // 0xd52079
	colorMap.put("midnight blue", new Color(47, 47, 100)); // 0x2f2f64
	colorMap.put("mint cream", new Color(245, 255, 250)); // 0xf5fffa
	colorMap.put("misty rose", new Color(255, 228, 225)); // 0xffe4e1
	colorMap.put("moccasin", new Color(255, 228, 181)); // 0xffe4b5
	colorMap.put("navajo white", new Color(255, 222, 173)); // 0xffdead
	colorMap.put("navy", new Color(35, 35, 117)); // 0x232375
	colorMap.put("navy blue", new Color(35, 35, 117)); // 0x232375
	colorMap.put("old lace", new Color(253, 245, 230)); // 0xfdf5e6
	colorMap.put("olive drab", new Color(107, 142, 35)); // 0x6b8e23
	colorMap.put("orange", new Color(255, 135, 0)); // 0xff8700
	colorMap.put("orange red", new Color(255, 69, 0)); // 0xff4500
	colorMap.put("orange1", new Color(255, 165, 0)); // 0xffa500
	colorMap.put("orange2", new Color(238, 154, 0)); // 0xee9a00
	colorMap.put("orange3", new Color(205, 133, 0)); // 0xcd8500
	colorMap.put("orange4", new Color(139, 90, 0)); // 0x8b5a00
	colorMap.put("orchid", new Color(239, 132, 239)); // 0xef84ef
	colorMap.put("orchid1", new Color(255, 131, 250)); // 0xff83fa
	colorMap.put("orchid2", new Color(238, 122, 233)); // 0xee7ae9
	colorMap.put("orchid3", new Color(205, 105, 201)); // 0xcd69c9
	colorMap.put("orchid4", new Color(139, 71, 137)); // 0x8b4789
	colorMap.put("pale goldenrod", new Color(238, 232, 170)); // 0xeee8aa
	colorMap.put("pale green", new Color(115, 222, 120)); // 0x73de78
	colorMap.put("pale turquoise", new Color(175, 238, 238)); // 0xafeeee
	colorMap.put("pale violet red", new Color(219, 112, 147)); // 0xdb7093
	colorMap.put("papaya whip", new Color(255, 239, 213)); // 0xffefd5
	colorMap.put("peach puff", new Color(255, 218, 185)); // 0xffdab9
	colorMap.put("peru", new Color(205, 133, 63)); // 0xcd853f
	colorMap.put("pink", new Color(255, 181, 197)); // 0xffb5c5
	colorMap.put("pink1", new Color(255, 181, 197)); // 0xffb5c5
	colorMap.put("pink2", new Color(238, 169, 184)); // 0xeea9b8
	colorMap.put("pink3", new Color(205, 145, 158)); // 0xcd919e
	colorMap.put("pink4", new Color(139, 99, 108)); // 0x8b636c
	colorMap.put("plum", new Color(197, 72, 155)); // 0xc5489b
	colorMap.put("plum1", new Color(255, 187, 255)); // 0xffbbff
	colorMap.put("plum2", new Color(238, 174, 238)); // 0xeeaeee
	colorMap.put("plum3", new Color(205, 150, 205)); // 0xcd96cd
	colorMap.put("plum4", new Color(139, 102, 139)); // 0x8b668b
	colorMap.put("powder blue", new Color(176, 224, 230)); // 0xb0e0e6
	colorMap.put("purple", new Color(160, 32, 240)); // 0xa020f0
	colorMap.put("purple1", new Color(155, 48, 255)); // 0x9b30ff
	colorMap.put("purple2", new Color(145, 44, 238)); // 0x912cee
	colorMap.put("purple3", new Color(125, 38, 205)); // 0x7d26cd
	colorMap.put("purple4", new Color(85, 26, 139)); // 0x551a8b
	colorMap.put("red", new Color(255, 0, 0)); // 0xff0000
	colorMap.put("red1", new Color(255, 0, 0)); // 0xff0000
	colorMap.put("red2", new Color(238, 0, 0)); // 0xee0000
	colorMap.put("red3", new Color(205, 0, 0)); // 0xcd0000
	colorMap.put("red4", new Color(139, 0, 0)); // 0x8b0000
	colorMap.put("rosy brown", new Color(188, 143, 143)); // 0xbc8f8f
	colorMap.put("royal blue", new Color(65, 105, 225)); // 0x4169e1
	colorMap.put("saddle brown", new Color(139, 69, 19)); // 0x8b4513
	colorMap.put("salmon", new Color(233, 150, 122)); // 0xe9967a
	colorMap.put("salmon1", new Color(255, 140, 105)); // 0xff8c69
	colorMap.put("salmon2", new Color(238, 130, 98)); // 0xee8262
	colorMap.put("salmon3", new Color(205, 112, 84)); // 0xcd7054
	colorMap.put("salmon4", new Color(139, 76, 57)); // 0x8b4c39
	colorMap.put("sandy brown", new Color(244, 164, 96)); // 0xf4a460
	colorMap.put("sea green", new Color(82, 149, 132)); // 0x529584
	colorMap.put("seashell", new Color(255, 245, 238)); // 0xfff5ee
	colorMap.put("seashell1", new Color(255, 245, 238)); // 0xfff5ee
	colorMap.put("seashell2", new Color(238, 229, 222)); // 0xeee5de
	colorMap.put("seashell3", new Color(205, 197, 191)); // 0xcdc5bf
	colorMap.put("seashell4", new Color(139, 134, 130)); // 0x8b8682
	colorMap.put("sienna", new Color(150, 82, 45)); // 0x96522d
	colorMap.put("sienna1", new Color(255, 130, 71)); // 0xff8247
	colorMap.put("sienna2", new Color(238, 121, 66)); // 0xee7942
	colorMap.put("sienna3", new Color(205, 104, 57)); // 0xcd6839
	colorMap.put("sienna4", new Color(139, 71, 38)); // 0x8b4726
	colorMap.put("sky blue", new Color(114, 159, 255)); // 0x729fff
	colorMap.put("slate blue", new Color(126, 136, 171)); // 0x7e88ab
	colorMap.put("slate gray", new Color(112, 128, 144)); // 0x708090
	colorMap.put("slate grey", new Color(112, 128, 144)); // 0x708090
	colorMap.put("snow", new Color(255, 250, 250)); // 0xfffafa
	colorMap.put("snow1", new Color(255, 250, 250)); // 0xfffafa
	colorMap.put("snow2", new Color(238, 233, 233)); // 0xeee9e9
	colorMap.put("snow3", new Color(205, 201, 201)); // 0xcdc9c9
	colorMap.put("snow4", new Color(139, 137, 137)); // 0x8b8989
	colorMap.put("spring green", new Color(65, 172, 65)); // 0x41ac41
	colorMap.put("steel blue", new Color(84, 112, 170)); // 0x5470aa
	colorMap.put("tan", new Color(222, 184, 135)); // 0xdeb887
	colorMap.put("tan1", new Color(255, 165, 79)); // 0xffa54f
	colorMap.put("tan2", new Color(238, 154, 73)); // 0xee9a49
	colorMap.put("tan3", new Color(205, 133, 63)); // 0xcd853f
	colorMap.put("tan4", new Color(139, 90, 43)); // 0x8b5a2b
	colorMap.put("thistle", new Color(216, 191, 216)); // 0xd8bfd8
	colorMap.put("thistle1", new Color(255, 225, 255)); // 0xffe1ff
	colorMap.put("thistle2", new Color(238, 210, 238)); // 0xeed2ee
	colorMap.put("thistle3", new Color(205, 181, 205)); // 0xcdb5cd
	colorMap.put("thistle4", new Color(139, 123, 139)); // 0x8b7b8b
	colorMap.put("tomato", new Color(255, 99, 71)); // 0xff6347
	colorMap.put("tomato1", new Color(255, 99, 71)); // 0xff6347
	colorMap.put("tomato2", new Color(238, 92, 66)); // 0xee5c42
	colorMap.put("tomato3", new Color(205, 79, 57)); // 0xcd4f39
	colorMap.put("tomato4", new Color(139, 54, 38)); // 0x8b3626
	colorMap.put("transparent", new Color(0, 0, 1)); // 0x000001
	colorMap.put("turquoise", new Color(25, 204, 223)); // 0x19ccdf
	colorMap.put("turquoise1", new Color(0, 245, 255)); // 0x00f5ff
	colorMap.put("turquoise2", new Color(0, 229, 238)); // 0x00e5ee
	colorMap.put("turquoise3", new Color(0, 197, 205)); // 0x00c5cd
	colorMap.put("turquoise4", new Color(0, 134, 139)); // 0x00868b
	colorMap.put("violet", new Color(156, 62, 206)); // 0x9c3ece
	colorMap.put("violet red", new Color(243, 62, 150)); // 0xf33e96
	colorMap.put("wheat", new Color(245, 222, 179)); // 0xf5deb3
	colorMap.put("wheat1", new Color(255, 231, 186)); // 0xffe7ba
	colorMap.put("wheat2", new Color(238, 216, 174)); // 0xeed8ae
	colorMap.put("wheat3", new Color(205, 186, 150)); // 0xcdba96
	colorMap.put("wheat4", new Color(139, 126, 102)); // 0x8b7e66
	colorMap.put("white", new Color(255, 255, 255)); // 0xffffff
	colorMap.put("white smoke", new Color(245, 245, 245)); // 0xf5f5f5
	colorMap.put("yellow", new Color(255, 255, 0)); // 0xffff00
	colorMap.put("yellow green", new Color(50, 216, 56)); // 0x32d838
	colorMap.put("yellow1", new Color(255, 255, 0)); // 0xffff00
	colorMap.put("yellow2", new Color(238, 238, 0)); // 0xeeee00
	colorMap.put("yellow3", new Color(205, 205, 0)); // 0xcdcd00
	colorMap.put("yellow4", new Color(139, 139, 0)); // 0x8b8b00
    }

    static {
	nameMap.put("000000", "Black");
	nameMap.put("000001", "Transparent");
	nameMap.put("00008b", "blue4");
	nameMap.put("0000cd", "blue3");
	nameMap.put("0000ee", "blue2");
	nameMap.put("0000ff", "Blue");
	nameMap.put("00562d", "DarkGreen");
	nameMap.put("00688b", "DeepSkyBlue4");
	nameMap.put("00868b", "turquoise4");
	nameMap.put("008b00", "green4");
	nameMap.put("008b45", "SpringGreen4");
	nameMap.put("008b8b", "cyan4");
	nameMap.put("00938f", "MediumAquamarine");
	nameMap.put("009acd", "DeepSkyBlue3");
	nameMap.put("00a6a6", "DarkTurquoise");
	nameMap.put("00af14", "LimeGreen");
	nameMap.put("00b2ee", "DeepSkyBlue2");
	nameMap.put("00bfff", "DeepSkyBlue1");
	nameMap.put("00c5cd", "turquoise3");
	nameMap.put("00cd00", "green3");
	nameMap.put("00cd66", "SpringGreen3");
	nameMap.put("00cdcd", "cyan3");
	nameMap.put("00d2d2", "MediumTurquoise");
	nameMap.put("00e5ee", "turquoise2");
	nameMap.put("00ee00", "green2");
	nameMap.put("00ee76", "SpringGreen2");
	nameMap.put("00eeee", "cyan2");
	nameMap.put("00f5ff", "turquoise1");
	nameMap.put("00ff00", "Green");
	nameMap.put("00ff7f", "SpringGreen1");
	nameMap.put("00ffff", "Cyan");
	nameMap.put("030303", "Gray1");
	nameMap.put("050505", "Gray2");
	nameMap.put("080808", "Gray3");
	nameMap.put("0a0a0a", "Gray4");
	nameMap.put("0d0d0d", "Gray5");
	nameMap.put("0f0f0f", "Gray6");
	nameMap.put("104e8b", "DodgerBlue4");
	nameMap.put("121212", "Gray7");
	nameMap.put("141414", "Gray8");
	nameMap.put("171717", "Gray9");
	nameMap.put("1874cd", "DodgerBlue3");
	nameMap.put("19ccdf", "Turquoise");
	nameMap.put("1a1a1a", "Gray10");
	nameMap.put("1c1c1c", "Gray11");
	nameMap.put("1c86ee", "DodgerBlue2");
	nameMap.put("1e90ff", "DodgerBlue");
	nameMap.put("1f1f1f", "Gray12");
	nameMap.put("20b2aa", "LightSeaGreen");
	nameMap.put("212121", "Gray13");
	nameMap.put("222298", "CornflowerBlue");
	nameMap.put("232375", "Navy");
	nameMap.put("238e23", "MediumSpringGreen");
	nameMap.put("242424", "Gray14");
	nameMap.put("262626", "Gray15");
	nameMap.put("27408b", "RoyalBlue4");
	nameMap.put("292929", "Gray16");
	nameMap.put("2b2b2b", "Gray17");
	nameMap.put("2e2e2e", "Gray18");
	nameMap.put("2e8b57", "SeaGreen4");
	nameMap.put("2f2f64", "MidnightBlue");
	nameMap.put("2f4f4f", "DarkSlateGray");
	nameMap.put("303030", "Gray19");
	nameMap.put("3232cc", "MediumBlue");
	nameMap.put("32814b", "MediumForestGreen");
	nameMap.put("32bfc1", "Aquamarine");
	nameMap.put("32d838", "YellowGreen");
	nameMap.put("333333", "Gray20");
	nameMap.put("347766", "MediumSeaGreen");
	nameMap.put("363636", "Gray21");
	nameMap.put("36648b", "SteelBlue4");
	nameMap.put("383838", "Gray22");
	nameMap.put("384b66", "DarkSlateBlue");
	nameMap.put("3a5fcd", "RoyalBlue3");
	nameMap.put("3b3b3b", "Gray23");
	nameMap.put("3d3d3d", "Gray24");
	nameMap.put("404040", "Gray25");
	nameMap.put("4169e1", "RoyalBlue");
	nameMap.put("41ac41", "SpringGreen");
	nameMap.put("424242", "Gray26");
	nameMap.put("436eee", "RoyalBlue2");
	nameMap.put("43cd80", "SeaGreen3");
	nameMap.put("454545", "Gray27");
	nameMap.put("458b00", "chartreuse4");
	nameMap.put("458b74", "aquamarine4");
	nameMap.put("473c8b", "SlateBlue4");
	nameMap.put("474747", "Gray28");
	nameMap.put("4876ff", "RoyalBlue1");
	nameMap.put("4a4a4a", "Gray29");
	nameMap.put("4a708b", "SkyBlue4");
	nameMap.put("4d4d4d", "Gray30");
	nameMap.put("4eee94", "SeaGreen2");
	nameMap.put("4f4f4f", "Gray31");
	nameMap.put("4f94cd", "SteelBlue3");
	nameMap.put("509f69", "ForestGreen");
	nameMap.put("525252", "Gray32");
	nameMap.put("528b8b", "DarkSlateGray4");
	nameMap.put("529584", "SeaGreen");
	nameMap.put("53868b", "CadetBlue4");
	nameMap.put("545454", "DimGray");
	nameMap.put("5470aa", "SteelBlue");
	nameMap.put("548b54", "PaleGreen4");
	nameMap.put("54ff9f", "SeaGreen1");
	nameMap.put("551a8b", "purple4");
	nameMap.put("55562f", "DarkOliveGreen");
	nameMap.put("575757", "Gray34");
	nameMap.put("595959", "Gray35");
	nameMap.put("5c5c5c", "Gray36");
	nameMap.put("5cacee", "SteelBlue2");
	nameMap.put("5d478b", "MediumPurple4");
	nameMap.put("5e5e5e", "Gray37");
	nameMap.put("5f929e", "CadetBlue");
	nameMap.put("607b8b", "LightSkyBlue4");
	nameMap.put("616161", "Gray38");
	nameMap.put("636363", "Gray39");
	nameMap.put("63b8ff", "SteelBlue1");
	nameMap.put("666666", "Gray40");
	nameMap.put("668b8b", "PaleTurquoise4");
	nameMap.put("66cd00", "chartreuse3");
	nameMap.put("66cdaa", "aquamarine3");
	nameMap.put("68228b", "DarkOrchid4");
	nameMap.put("68838b", "LightBlue4");
	nameMap.put("6959cd", "SlateBlue3");
	nameMap.put("696969", "Gray41");
	nameMap.put("698b22", "OliveDrab4");
	nameMap.put("698b69", "DarkSeaGreen4");
	nameMap.put("6a6a8d", "MediumSlateBlue");
	nameMap.put("6b3939", "IndianRed");
	nameMap.put("6b6b6b", "Gray42");
	nameMap.put("6b8e23", "OliveDrab");
	nameMap.put("6c7b8b", "SlateGray4");
	nameMap.put("6ca6cd", "SkyBlue3");
	nameMap.put("6e6e6e", "Gray43");
	nameMap.put("6e7b8b", "LightSteelBlue4");
	nameMap.put("6e8b3d", "DarkOliveGreen4");
	nameMap.put("707070", "Gray44");
	nameMap.put("708090", "SlateGray");
	nameMap.put("729fff", "SkyBlue");
	nameMap.put("737373", "Gray45");
	nameMap.put("73de78", "PaleGreen");
	nameMap.put("757575", "Gray46");
	nameMap.put("76ee00", "chartreuse2");
	nameMap.put("76eec6", "aquamarine2");
	nameMap.put("778899", "LightSlateGray");
	nameMap.put("787878", "Gray47");
	nameMap.put("79cdcd", "DarkSlateGray3");
	nameMap.put("7a378b", "MediumOrchid4");
	nameMap.put("7a67ee", "SlateBlue2");
	nameMap.put("7a7a7a", "Gray48");
	nameMap.put("7a8b8b", "LightCyan4");
	nameMap.put("7ac5cd", "CadetBlue3");
	nameMap.put("7c98d3", "LightSteelBlue");
	nameMap.put("7ccd7c", "PaleGreen3");
	nameMap.put("7cfc00", "LawnGreen");
	nameMap.put("7d26cd", "purple3");
	nameMap.put("7d7d7d", "Gray49");
	nameMap.put("7e7e7e", "Gray");
	nameMap.put("7e88ab", "SlateBlue");
	nameMap.put("7ec0ee", "SkyBlue2");
	nameMap.put("7f7f7f", "Gray50");
	nameMap.put("7fff00", "chartreuse");
	nameMap.put("7fffd4", "aquamarine1");
	nameMap.put("800000", "BrickRed");
	nameMap.put("828282", "Gray51");
	nameMap.put("836fff", "SlateBlue1");
	nameMap.put("838b83", "honeydew4");
	nameMap.put("838b8b", "azure4");
	nameMap.put("8470ff", "LightSlateBlue");
	nameMap.put("858585", "Gray52");
	nameMap.put("878787", "Gray53");
	nameMap.put("87cefa", "LightSkyBlue");
	nameMap.put("87ceff", "SkyBlue1");
	nameMap.put("8968cd", "MediumPurple3");
	nameMap.put("8a2be2", "BlueViolet");
	nameMap.put("8a8a8a", "Gray54");
	nameMap.put("8b0000", "red4");
	nameMap.put("8b008b", "magenta4");
	nameMap.put("8b0a50", "DeepPink4");
	nameMap.put("8b1a1a", "firebrick4");
	nameMap.put("8b1c62", "maroon4");
	nameMap.put("8b208b", "DarkOrchid");
	nameMap.put("8b2252", "VioletRed4");
	nameMap.put("8b2323", "brown4");
	nameMap.put("8b2500", "OrangeRed4");
	nameMap.put("8b3626", "tomato4");
	nameMap.put("8b3a3a", "IndianRed4");
	nameMap.put("8b3a62", "HotPink4");
	nameMap.put("8b3e2f", "coral4");
	nameMap.put("8b4500", "DarkOrange4");
	nameMap.put("8b4513", "SaddleBrown");
	nameMap.put("8b4726", "sienna4");
	nameMap.put("8b475d", "PaleVioletRed4");
	nameMap.put("8b4789", "orchid4");
	nameMap.put("8b4c39", "salmon4");
	nameMap.put("8b5742", "LightSalmon4");
	nameMap.put("8b5a00", "orange4");
	nameMap.put("8b5a2b", "tan4");
	nameMap.put("8b5f65", "LightPink4");
	nameMap.put("8b636c", "pink4");
	nameMap.put("8b6508", "DarkGoldenrod4");
	nameMap.put("8b668b", "plum4");
	nameMap.put("8b6914", "goldenrod4");
	nameMap.put("8b6969", "RosyBrown4");
	nameMap.put("8b7355", "burlywood4");
	nameMap.put("8b7500", "gold4");
	nameMap.put("8b7765", "PeachPuff4");
	nameMap.put("8b795e", "NavajoWhite4");
	nameMap.put("8b7b8b", "thistle4");
	nameMap.put("8b7d6b", "bisque4");
	nameMap.put("8b7d7b", "MistyRose4");
	nameMap.put("8b7e66", "wheat4");
	nameMap.put("8b814c", "LightGoldenrod4");
	nameMap.put("8b8378", "AntiqueWhite4");
	nameMap.put("8b8386", "LavenderBlush4");
	nameMap.put("8b864e", "khaki4");
	nameMap.put("8b8682", "seashell4");
	nameMap.put("8b8878", "cornsilk4");
	nameMap.put("8b8970", "LemonChiffon4");
	nameMap.put("8b8989", "snow4");
	nameMap.put("8b8b00", "yellow4");
	nameMap.put("8b8b7a", "LightYellow4");
	nameMap.put("8b8b83", "ivory4");
	nameMap.put("8c8c8c", "Gray55");
	nameMap.put("8db6cd", "LightSkyBlue3");
	nameMap.put("8deeee", "DarkSlateGray2");
	nameMap.put("8e2323", "Firebrick");
	nameMap.put("8ee5ee", "CadetBlue2");
	nameMap.put("8f0052", "Maroon");
	nameMap.put("8f8f8f", "Gray56");
	nameMap.put("8fbc8f", "DarkSeaGreen");
	nameMap.put("90ee90", "PaleGreen2");
	nameMap.put("912cee", "purple2");
	nameMap.put("919191", "Gray57");
	nameMap.put("9370db", "MediumPurple");
	nameMap.put("9400d3", "DarkViolet");
	nameMap.put("949494", "Gray58");
	nameMap.put("96522d", "Sienna");
	nameMap.put("969696", "Gray59");
	nameMap.put("96cdcd", "PaleTurquoise3");
	nameMap.put("97ffff", "DarkSlateGray1");
	nameMap.put("98f5ff", "CadetBlue1");
	nameMap.put("999999", "Gray60");
	nameMap.put("9a32cd", "DarkOrchid3");
	nameMap.put("9ac0cd", "LightBlue3");
	nameMap.put("9acd32", "OliveDrab3");
	nameMap.put("9aff9a", "PaleGreen1");
	nameMap.put("9b30ff", "purple1");
	nameMap.put("9bcd9b", "DarkSeaGreen3");
	nameMap.put("9c3ece", "Violet");
	nameMap.put("9c9c9c", "Gray61");
	nameMap.put("9e9e9e", "Gray62");
	nameMap.put("9f79ee", "MediumPurple2");
	nameMap.put("9fb6cd", "SlateGray3");
	nameMap.put("a020f0", "purple");
	nameMap.put("a1a1a1", "Gray63");
	nameMap.put("a2b5cd", "LightSteelBlue3");
	nameMap.put("a2cd5a", "DarkOliveGreen3");
	nameMap.put("a3a3a3", "Gray64");
	nameMap.put("a4d3ee", "LightSkyBlue2");
	nameMap.put("a52a2a", "Brown");
	nameMap.put("a52a2a", "brown");
	nameMap.put("a6a6a6", "Gray65");
	nameMap.put("a8a8a8", "Gray66");
	nameMap.put("ab82ff", "MediumPurple1");
	nameMap.put("ababab", "Gray67");
	nameMap.put("adadad", "Gray68");
	nameMap.put("adff2f", "GreenYellow");
	nameMap.put("aeeeee", "PaleTurquoise2");
	nameMap.put("afeeee", "PaleTurquoise");
	nameMap.put("b0b0b0", "Gray69");
	nameMap.put("b0e0e6", "PowderBlue");
	nameMap.put("b0e2ff", "LightBlue");
	nameMap.put("b23aee", "DarkOrchid2");
	nameMap.put("b2dfee", "LightBlue2");
	nameMap.put("b3b37e", "Khaki");
	nameMap.put("b3b3b3", "Gray70");
	nameMap.put("b3ee3a", "OliveDrab2");
	nameMap.put("b452cd", "MediumOrchid3");
	nameMap.put("b4cdcd", "LightCyan3");
	nameMap.put("b4eeb4", "DarkSeaGreen2");
	nameMap.put("b5b5b5", "Gray71");
	nameMap.put("b8860b", "DarkGoldenrod");
	nameMap.put("b8b8b8", "Gray72");
	nameMap.put("b9d3ee", "SlateGray2");
	nameMap.put("bababa", "Gray73");
	nameMap.put("bbffff", "PaleTurquoise1");
	nameMap.put("bc8f8f", "RosyBrown");
	nameMap.put("bcd2ee", "LightSteelBlue2");
	nameMap.put("bcee68", "DarkOliveGreen2");
	nameMap.put("bd52bd", "MediumOrchid");
	nameMap.put("bdb76b", "DarkKhaki");
	nameMap.put("bdbdbd", "Gray74");
	nameMap.put("bf3eff", "DarkOrchid1");
	nameMap.put("bfbfbf", "Gray75");
	nameMap.put("bfefff", "LightBlue1");
	nameMap.put("c0ff3e", "OliveDrab1");
	nameMap.put("c1cdc1", "honeydew3");
	nameMap.put("c1cdcd", "azure3");
	nameMap.put("c1ffc1", "DarkSeaGreen1");
	nameMap.put("c2c2c2", "Gray76");
	nameMap.put("c4c4c4", "Gray77");
	nameMap.put("c5489b", "Plum");
	nameMap.put("c6e2ff", "SlateGray1");
	nameMap.put("c7c7c7", "Gray78");
	nameMap.put("c9c9c9", "Gray79");
	nameMap.put("cae1ff", "LightSteelBlue1");
	nameMap.put("caff70", "DarkOliveGreen1");
	nameMap.put("cccccc", "Gray80");
	nameMap.put("cd0000", "red3");
	nameMap.put("cd00cd", "magenta3");
	nameMap.put("cd1076", "DeepPink3");
	nameMap.put("cd2626", "firebrick3");
	nameMap.put("cd2990", "maroon3");
	nameMap.put("cd3278", "VioletRed3");
	nameMap.put("cd3333", "brown3");
	nameMap.put("cd3700", "OrangeRed3");
	nameMap.put("cd4f39", "tomato3");
	nameMap.put("cd5555", "IndianRed3");
	nameMap.put("cd5b45", "coral3");
	nameMap.put("cd6090", "HotPink3");
	nameMap.put("cd6600", "DarkOrange3");
	nameMap.put("cd661d", "chocolate3");
	nameMap.put("cd6839", "sienna3");
	nameMap.put("cd6889", "PaleVioletRed3");
	nameMap.put("cd69c9", "orchid3");
	nameMap.put("cd7054", "salmon3");
	nameMap.put("cd8162", "LightSalmon3");
	nameMap.put("cd8500", "orange3");
	nameMap.put("cd853f", "peru");
	nameMap.put("cd853f", "tan3");
	nameMap.put("cd8c95", "LightPink3");
	nameMap.put("cd919e", "pink3");
	nameMap.put("cd950c", "DarkGoldenrod3");
	nameMap.put("cd96cd", "plum3");
	nameMap.put("cd9b1d", "goldenrod3");
	nameMap.put("cd9b9b", "RosyBrown3");
	nameMap.put("cdaa7d", "burlywood3");
	nameMap.put("cdad00", "gold3");
	nameMap.put("cdaf95", "PeachPuff3");
	nameMap.put("cdb38b", "NavajoWhite3");
	nameMap.put("cdb5cd", "thistle3");
	nameMap.put("cdb79e", "bisque3");
	nameMap.put("cdb7b5", "MistyRose3");
	nameMap.put("cdba96", "wheat3");
	nameMap.put("cdbe70", "LightGoldenrod3");
	nameMap.put("cdc0b0", "AntiqueWhite3");
	nameMap.put("cdc1c5", "LavenderBlush3");
	nameMap.put("cdc5bf", "seashell3");
	nameMap.put("cdc673", "khaki3");
	nameMap.put("cdc8b1", "cornsilk3");
	nameMap.put("cdc9a5", "LemonChiffon3");
	nameMap.put("cdc9c9", "snow3");
	nameMap.put("cdcd00", "yellow3");
	nameMap.put("cdcdb4", "LightYellow3");
	nameMap.put("cdcdc1", "ivory3");
	nameMap.put("cfcfcf", "Gray81");
	nameMap.put("d15fee", "MediumOrchid2");
	nameMap.put("d1c166", "MediumGoldenrod");
	nameMap.put("d1d1d1", "Gray82");
	nameMap.put("d1eeee", "LightCyan2");
	nameMap.put("d2691e", "chocolate");
	nameMap.put("d4d4d4", "Gray83");
	nameMap.put("d52079", "MediumVioletRed");
	nameMap.put("d6d6d6", "Gray84");
	nameMap.put("d8bfd8", "Thistle");
	nameMap.put("d9d9d9", "Gray85");
	nameMap.put("daaa00", "Gold");
	nameMap.put("db7093", "PaleVioletRed");
	nameMap.put("dbdbdb", "Gray86");
	nameMap.put("dcdcdc", "gainsboro");
	nameMap.put("deb887", "Tan");
	nameMap.put("dedede", "Gray87");
	nameMap.put("e066ff", "MediumOrchid1");
	nameMap.put("e0e0e0", "Gray88");
	nameMap.put("e0eee0", "honeydew2");
	nameMap.put("e0eeee", "azure2");
	nameMap.put("e0ffff", "LightCyan");
	nameMap.put("e3e3e3", "Gray89");
	nameMap.put("e5e5e5", "Gray90");
	nameMap.put("e6e6fa", "lavender");
	nameMap.put("e8e8e8", "Gray91");
	nameMap.put("e9967a", "DarkSalmon");
	nameMap.put("ebebeb", "Gray92");
	nameMap.put("ededed", "Gray93");
	nameMap.put("ee0000", "red2");
	nameMap.put("ee00ee", "magenta2");
	nameMap.put("ee1289", "DeepPink2");
	nameMap.put("ee2c2c", "firebrick2");
	nameMap.put("ee30a7", "maroon2");
	nameMap.put("ee3a8c", "VioletRed2");
	nameMap.put("ee3b3b", "brown2");
	nameMap.put("ee4000", "OrangeRed2");
	nameMap.put("ee5c42", "tomato2");
	nameMap.put("ee6363", "IndianRed2");
	nameMap.put("ee6a50", "coral2");
	nameMap.put("ee6aa7", "HotPink2");
	nameMap.put("ee7600", "DarkOrange2");
	nameMap.put("ee7621", "chocolate2");
	nameMap.put("ee7942", "sienna2");
	nameMap.put("ee799f", "PaleVioletRed2");
	nameMap.put("ee7ae9", "orchid2");
	nameMap.put("ee8262", "salmon2");
	nameMap.put("ee9572", "LightSalmon2");
	nameMap.put("ee9a00", "orange2");
	nameMap.put("ee9a49", "tan2");
	nameMap.put("eea2ad", "LightPink2");
	nameMap.put("eea9b8", "pink2");
	nameMap.put("eead0e", "DarkGoldenrod2");
	nameMap.put("eeaeee", "plum2");
	nameMap.put("eeb422", "goldenrod2");
	nameMap.put("eeb4b4", "RosyBrown2");
	nameMap.put("eec591", "burlywood2");
	nameMap.put("eec900", "gold2");
	nameMap.put("eecbad", "PeachPuff2");
	nameMap.put("eecfa1", "NavajoWhite2");
	nameMap.put("eed2ee", "thistle2");
	nameMap.put("eed5b7", "bisque2");
	nameMap.put("eed5d2", "MistyRose2");
	nameMap.put("eed8ae", "wheat2");
	nameMap.put("eedc82", "LightGoldenrod2");
	nameMap.put("eedd82", "LightGoldenrod");
	nameMap.put("eedd82", "light goldenrod");
	nameMap.put("eedfcc", "AntiqueWhite2");
	nameMap.put("eee0e5", "LavenderBlush2");
	nameMap.put("eee5de", "seashell2");
	nameMap.put("eee685", "khaki2");
	nameMap.put("eee8aa", "PaleGoldenrod");
	nameMap.put("eee8aa", "pale goldenrod");
	nameMap.put("eee8cd", "cornsilk2");
	nameMap.put("eee9bf", "LemonChiffon2");
	nameMap.put("eee9e9", "snow2");
	nameMap.put("eeee00", "yellow2");
	nameMap.put("eeeed1", "LightYellow2");
	nameMap.put("eeeee0", "ivory2");
	nameMap.put("ef84ef", "Orchid");
	nameMap.put("efdf84", "Goldenrod");
	nameMap.put("f08080", "LightCoral");
	nameMap.put("f0f0f0", "Gray94");
	nameMap.put("f0f8ff", "AliceBlue");
	nameMap.put("f0fff0", "honeydew");
	nameMap.put("f0ffff", "azure");
	nameMap.put("f2f2f2", "Gray95");
	nameMap.put("f33e96", "VioletRed");
	nameMap.put("f4a460", "SandyBrown");
	nameMap.put("f5deb3", "Wheat");
	nameMap.put("f5f5dc", "beige");
	nameMap.put("f5f5f5", "WhiteSmoke");
	nameMap.put("f5fffa", "MintCream");
	nameMap.put("f7f7f7", "Gray97");
	nameMap.put("f8f8ff", "GhostWhite");
	nameMap.put("faebd7", "AntiqueWhite");
	nameMap.put("faf0e6", "linen");
	nameMap.put("fafad2", "LightGoldenrodYellow");
	nameMap.put("fafafa", "Gray98");
	nameMap.put("fcfcfc", "Gray99");
	nameMap.put("fdf5e6", "OldLace");
	nameMap.put("ff0000", "Red");
	nameMap.put("ff00ff", "Magenta");
	nameMap.put("ff1493", "DeepPink");
	nameMap.put("ff3030", "firebrick1");
	nameMap.put("ff34b3", "maroon1");
	nameMap.put("ff3e96", "VioletRed1");
	nameMap.put("ff4040", "brown1");
	nameMap.put("ff4500", "OrangeRed");
	nameMap.put("ff6347", "tomato");
	nameMap.put("ff69b4", "HotPink");
	nameMap.put("ff6a6a", "IndianRed1");
	nameMap.put("ff6eb4", "HotPink1");
	nameMap.put("ff7256", "Coral");
	nameMap.put("ff7f00", "DarkOrange1");
	nameMap.put("ff7f24", "chocolate1");
	nameMap.put("ff8247", "sienna1");
	nameMap.put("ff82ab", "PaleVioletRed1");
	nameMap.put("ff83fa", "orchid1");
	nameMap.put("ff8700", "Orange");
	nameMap.put("ff8c00", "DarkOrange");
	nameMap.put("ff8c69", "salmon1");
	nameMap.put("ffa07a", "LightSalmon");
	nameMap.put("ffa500", "orange1");
	nameMap.put("ffa54f", "tan1");
	nameMap.put("ffaeb9", "LightPink1");
	nameMap.put("ffb5c5", "Pink");
	nameMap.put("ffb6c1", "LightPink");
	nameMap.put("ffb90f", "DarkGoldenrod1");
	nameMap.put("ffbbff", "plum1");
	nameMap.put("ffc125", "goldenrod1");
	nameMap.put("ffc1c1", "RosyBrown1");
	nameMap.put("ffd39b", "burlywood1");
	nameMap.put("ffd700", "gold1");
	nameMap.put("ffdab9", "PeachPuff");
	nameMap.put("ffdead", "NavajoWhite");
	nameMap.put("ffe1ff", "thistle1");
	nameMap.put("ffe4b5", "moccasin");
	nameMap.put("ffe4c4", "bisque");
	nameMap.put("ffe4e1", "MistyRose");
	nameMap.put("ffe7ba", "wheat1");
	nameMap.put("ffebcd", "BlanchedAlmond");
	nameMap.put("ffec8b", "LightGoldenrod1");
	nameMap.put("ffefd5", "PapayaWhip");
	nameMap.put("ffefdb", "AntiqueWhite1");
	nameMap.put("fff0f5", "LavenderBlush");
	nameMap.put("fff5ee", "seashell");
	nameMap.put("fff68f", "khaki1");
	nameMap.put("fff8dc", "cornsilk");
	nameMap.put("fffacd", "LemonChiffon");
	nameMap.put("fffaf0", "FloralWhite");
	nameMap.put("fffafa", "snow");
	nameMap.put("ffff00", "Yellow");
	nameMap.put("ffffe0", "LightYellow");
	nameMap.put("fffff0", "ivory");
	nameMap.put("ffffff", "White");
    }

    private final static int MapSize = colorMap.size();

    // Constructor
    public Xcolortable() {
    }

    /** Search for a color by name.
     *  CHxx:  Make static
     *  @param  String Color Name as known in X
     *  @return Color (if not found, return Color.black
     */
    public static Color getColor(String name) {
	if (!colorMap.containsKey(name))
	    return Color.black;
	else
	    return colorMap.get(name);
    }

    /**
     *  provide the list of color names in alphabetical order
     *  @return Sorted Vector of Names
     */
    public static SortedVector<String> getColorNames() {
	SortedVector<String> svect = new SortedVector<String>(MapSize);
	for (Iterator<String> i = colorMap.keySet().iterator(); i.hasNext();) {
	    svect.addString(i.next());
	}
	return svect;
    }

    /**
     *  Visual display of color choices
     *  @param SortedVector of color choices
     *  CH02
     */
    private void showColors(SortedVector<String> vec) {
	frame = new JFrame();
	GridBagConstraints gbc = new GridBagConstraints();
	frame.setTitle("Color Table");
	redSlider = new JSlider();
	blueSlider = new JSlider();
	greenSlider = new JSlider();
	colorArea = new JTextArea();
	colorName = new JComboBox<String>(vec);
	hexValue = new JLabel();
	Container c = frame.getContentPane();
	Color init = getColor(((String) vec.elementAt(1)));

	c.setLayout(new GridBagLayout());

	redSlider.setMaximum(255);
	redSlider.setName("red");
	redSlider.setMinimumSize(new Dimension(200, 20));
	redSlider.setBackground(Color.red);
	redSlider.setValue(init.getRed());
	redSlider.addChangeListener(this);

	blueSlider.setMaximum(255);
	blueSlider.setName("blue");
	blueSlider.setMinimumSize(new Dimension(200, 20));
	blueSlider.setBackground(Color.blue);
	blueSlider.setValue(init.getBlue());
	blueSlider.addChangeListener(this);

	greenSlider.setMaximum(255);
	greenSlider.setName("green");
	greenSlider.setMinimumSize(new Dimension(200, 20));
	greenSlider.setBackground(Color.green);
	greenSlider.setValue(init.getGreen());
	greenSlider.addChangeListener(this);

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 30;
	c.add(redSlider, gbc);

	gbc.gridy = 10;
	c.add(greenSlider, gbc);

	gbc.gridy = 20;
	c.add(blueSlider, gbc);

	gbc.gridy = 30;
	colorName.addItemListener(this);
	c.add(colorName, gbc);

	colorArea.setEditable(false);
	colorArea.setBackground(init);
	colorArea.setPreferredSize(new Dimension(120, 150));
	colorArea.setMinimumSize(new Dimension(100, 100));

	gbc.gridx = 30;
	gbc.gridy = 0;
	gbc.gridwidth = 60;
	gbc.gridheight = 60;
	c.add(colorArea, gbc);
	colorName.setToolTipText("Select standard X-system color");
	colorName.setSelectedIndex(1); // CHxx
	hexValue.setText("0x" + Integer.toHexString(init.getRGB()).substring(2));
	hexValue.setFont(new Font("Courier", 1, 12));

	gbc.gridx = 40;
	gbc.gridy = 65;
	gbc.anchor = GridBagConstraints.WEST;
	c.add(hexValue, gbc);
	frame.setSize(340, 235);
	frame.setVisible(true);
	RFFUtilities.setScreenCenter(frame);
    }

    /**
     *  Determine changes due to user changing sliders
     *  
     *  @param ChangeEvent
     *  CH02
     */
    public void stateChanged(ChangeEvent e) {
	JSlider source = (JSlider) e.getSource();
	if (!source.getValueIsAdjusting()) {
	    int val = (int) source.getValue();
	    String sval = Integer.toHexString(val);
	    Color col = colorArea.getBackground();
	    int r = col.getRed();
	    int g = col.getGreen();
	    int b = col.getBlue();
	    StringBuffer sb = new StringBuffer(hexValue.getText());
	    if (source.getName().equals("blue")) {
		sb.replace(6, 8, sval);
		b = val;
	    } else if (source.getName().equals("green")) {
		sb.replace(4, 6, sval);
		g = val;
	    } else {
		sb.replace(2, 4, sval);
		r = val;
	    }
	    // hexValue.setText(sb.toString());
	    hexValue.setText("0x" + toHex(r) + toHex(g) + toHex(b));
	    colorArea.setBackground(new Color(r, g, b));
	    int ix = findClosestIndex(r, g, b); // CHxx
	    colorName.setSelectedIndex(ix); // CHxx
	}
    }

    /**
     *  Find the associated color name. return the index.
     *  if not found, return 0 (aka unknown)
     *  @param int red
     *  @param int green
     *  @param int blue
     *  @return int index
     */
    public int findClosestIndex(int r, int g, int b) {
	int ix = 0;
	String hex = toHex(r) + toHex(g) + toHex(b);
	String colorname = nameMap.get(hex);
	if (colorname != null && colorname.length() > 0) {
	    SortedVector<String> v = getColorNames();
	    ix = v.indexOf(colorname);
	}
	return ix;
    }

    /**
     *  Return 2character hex representation
     *  @param int
     *  @return String
     */
    private String toHex(int i) {
	String h = Integer.toHexString(i);
	if (i < 16) {
	    return "0" + h;
	}
	return h;
    }

    /**
     *  Changes due to selecting from Color names
     *  @param ItemEvent
     *  CH02
     */
    public void itemStateChanged(ItemEvent e) {
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    JComboBox<String> jc = (JComboBox<String>) e.getSource();
	    String name = (String) jc.getSelectedItem();
	    if (name.equals("**** UNKNOWN ****"))
		return;
	    Color col = getColor(name);
	    colorArea.setBackground(col);
	    redSlider.removeChangeListener(this);
	    blueSlider.removeChangeListener(this);
	    greenSlider.removeChangeListener(this);
	    redSlider.setValue(col.getRed());
	    blueSlider.setValue(col.getBlue());
	    greenSlider.setValue(col.getGreen());
	    redSlider.addChangeListener(this);
	    blueSlider.addChangeListener(this);
	    greenSlider.addChangeListener(this);
	    hexValue.setText("0x" + Integer.toHexString(col.getRGB()).substring(2));
	}
    }

    // Window Listener events
    /**
     * Required stub for window listener
     * @param e Window Event
     */
    public void windowIconified(WindowEvent e) {
    }

    /**
     Required stub for window listener
     * @param e Window Event
     */
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     Required stub for window listener
     * @param e Window Event
     */
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     Required stub for window listener
     * @param e Window Event
     */
    public void windowOpened(WindowEvent e) {
    }

    /**
     Required stub for window listener
     * @param e Window Event
     */
    public void windowActivated(WindowEvent e) {
    }

    /**
     Required stub for window listener
     * @param e Window Event
     */
    public void windowClosed(WindowEvent e) {
    }

    /**
     *  WindowClosing Event
     *  @param WindowEvent
     */
    public void windowClosing(WindowEvent e) {
	System.exit(0);
    }

    /** 
    * Standalone entry point.
    * Usage:  java Xcolortable
    */
    public static void main(String[] args) {
	Xcolortable xct = new Xcolortable();
	Color skb;
	SortedVector<String> v = Xcolortable.getColorNames();
        xct.showColors(v);
	}
}









