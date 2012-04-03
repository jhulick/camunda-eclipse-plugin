/*******************************************************************************
 * Copyright (c) 2011 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.preferences;

import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
import org.eclipse.graphiti.mm.algorithms.styles.StylesPackage;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.ColorUtil;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

/**
 * @author Bob Brodt
 *
 */
public class ShapeStyle {

	IColorConstant shapeDefaultColor;
	IColorConstant shapePrimarySelectedColor;
	IColorConstant shapeSecondarySelectedColor;
	IColorConstant shapeBorderColor;
	Font textFont;
	IColorConstant textColor;
	boolean dirty;

	public ShapeStyle() {
		shapeDefaultColor = new ColorConstant(116, 143, 165);
		shapePrimarySelectedColor = new ColorConstant(116+16, 143+16, 165+16);
		shapeSecondarySelectedColor = new ColorConstant(116-16, 143-16, 165-16);
		shapeBorderColor = new ColorConstant(116-16-16, 143-16-16, 165-16-16);
		textFont = stringToFont("arial,10,-,B");
		textColor = IColorConstant.BLACK;
	}
	
	protected ShapeStyle(String s) {
		String[] a = s.trim().split(";");
		shapeDefaultColor = stringToColor(a[0]);
		shapePrimarySelectedColor = stringToColor(a[1]);
		shapeSecondarySelectedColor = stringToColor(a[2]);
		shapeBorderColor = stringToColor(a[3]);
		textFont = stringToFont(a[4]);
		textColor = stringToColor(a[5]);
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public IColorConstant getShapeDefaultColor() {
		return shapeDefaultColor;
	}

	public void setShapeDefaultColor(IColorConstant shapeDefaultColor) {
		if (!compare(this.shapeDefaultColor, shapeDefaultColor)) {
			this.shapeDefaultColor = shapeDefaultColor;
			setDirty(true);
		}
	}

	public IColorConstant getShapePrimarySelectedColor() {
		return shapePrimarySelectedColor;
	}

	public void setShapePrimarySelectedColor(IColorConstant shapePrimarySelectedColor) {
		if (!compare(this.shapePrimarySelectedColor, shapePrimarySelectedColor)) {
			this.shapePrimarySelectedColor = shapePrimarySelectedColor;
			setDirty(true);
		}
	}

	public IColorConstant getShapeSecondarySelectedColor() {
		return shapeSecondarySelectedColor;
	}

	public void setShapeSecondarySelectedColor(IColorConstant shapeSecondarySelectedColor) {
		if (!compare(this.shapeSecondarySelectedColor, shapeSecondarySelectedColor)) {
			this.shapeSecondarySelectedColor = shapeSecondarySelectedColor;
			setDirty(true);
		}
	}

	public IColorConstant getShapeBorderColor() {
		return shapeBorderColor;
	}

	public void setShapeBorderColor(IColorConstant shapeBorderColor) {
		if (!compare(this.shapeBorderColor, shapeBorderColor)) {
			this.shapeBorderColor = shapeBorderColor;
			setDirty(true);
		}
	}

	public Font getTextFont() {
		return textFont;
	}

	public void setTextFont(Font textFont) {
		if (!compare(this.textFont, textFont)) {
			this.textFont = textFont;
			setDirty(true);
		}
	}

	public IColorConstant getTextColor() {
		return textColor;
	}

	public void setTextColor(IColorConstant textColor) {
		if (!compare(this.textColor, textColor)) {
			this.textColor = textColor;
			setDirty(true);
		}
	}
	
	public static String colorToString(IColorConstant c) {
		return new String(
				String.format("%02X",c.getRed()) +
				String.format("%02X",c.getGreen()) +
				String.format("%02X",c.getBlue())
				);
	}
	
	public static IColorConstant stringToColor(String s) {
		if (s.length()<6)
			return new ColorConstant(0,0,0);
		return new ColorConstant(
				ColorUtil.getRedFromHex(s),
				ColorUtil.getGreenFromHex(s),
				ColorUtil.getBlueFromHex(s)
				);
	}
	
	public static RGB colorToRGB(IColorConstant c) {
		return new RGB(c.getRed(),c.getGreen(),c.getBlue());
	}
	
	public static IColorConstant RGBToColor(RGB rgb) {
		return new ColorConstant(rgb.red, rgb.green, rgb.blue);
	}

	public static String fontToString(Font f) {
		return new String(
				f.getName() + "," +
				f.getSize() + "," +
				(f.isItalic() ? "I" : "-") + "," +
				(f.isBold() ? "B" : "-")
				);
	}
	
	public static Font stringToFont(String s) {
		String[] a = s.split(",");
		Font f = StylesFactory.eINSTANCE.createFont();
		f.eSet(StylesPackage.eINSTANCE.getFont_Name(), a[0]);
		f.eSet(StylesPackage.eINSTANCE.getFont_Size(), Integer.valueOf(a[1]));
		f.eSet(StylesPackage.eINSTANCE.getFont_Italic(), a[2].equals("I"));
		f.eSet(StylesPackage.eINSTANCE.getFont_Bold(), a[3].equals("B"));
		return f;
	}

	public static FontData fontToFontData(Font f) {
		int style = 0;
		if (f.isItalic())
			style |= SWT.ITALIC;
		if (f.isBold())
			style |= SWT.BOLD;
		return new FontData(f.getName(), f.getSize(), style);
	}
	
	public static Font fontDataToFont(FontData fd) {
		Font f = StylesFactory.eINSTANCE.createFont();
		f.eSet(StylesPackage.eINSTANCE.getFont_Name(),fd.getName());
		f.eSet(StylesPackage.eINSTANCE.getFont_Size(), fd.getHeight());
		f.eSet(StylesPackage.eINSTANCE.getFont_Italic(), (fd.getStyle() & SWT.ITALIC)!=0);
		f.eSet(StylesPackage.eINSTANCE.getFont_Bold(), (fd.getStyle() & SWT.BOLD)!=0);
		return f;
	}
	
	public static String encode(ShapeStyle sp) {
		return new String(
				colorToString(sp.shapeDefaultColor) + ";" +
				colorToString(sp.shapePrimarySelectedColor) + ";" +
				colorToString(sp.shapeSecondarySelectedColor) + ";" +
				colorToString(sp.shapeBorderColor) + ";" +
				fontToString(sp.textFont) + ";" +
				colorToString(sp.textColor)
				);
	}
	
	public static ShapeStyle decode(String s) {
		if (s==null || s.trim().split(";").length!=6)
			return new ShapeStyle();
		return new ShapeStyle(s);
	}
	
	public static boolean compare(IColorConstant c1, IColorConstant c2) {
		return c1.getRed() == c2.getRed() &&
				c1.getGreen() == c2.getGreen() &&
				c1.getBlue() == c2.getBlue();
	}
	
	public static boolean compare(Font f1, Font f2) {
		String s1 = fontToString(f1);
		String s2 = fontToString(f2);
		return s1.equals(s2);
	}

	public static boolean compare(ShapeStyle s1, ShapeStyle s2) {
		return
				compare(s1.shapeDefaultColor, s2.shapeDefaultColor) ||
				compare(s1.shapePrimarySelectedColor, s2.shapePrimarySelectedColor) ||
				compare(s1.shapeSecondarySelectedColor, s2.shapeSecondarySelectedColor) ||
				compare(s1.shapeBorderColor, s2.shapeBorderColor) ||
				compare(s1.textFont, s2.textFont) ||
				compare(s1.textColor, s2.textColor);
	}
}
