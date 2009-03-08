package de.lessvoid.nifty.loaderv2.types;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.ElementRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.loaderv2.NiftyFactory;
import de.lessvoid.nifty.tools.StringHelper;
import de.lessvoid.xml.xpp3.Attributes;

public class TextType extends ElementType {
  public TextType() {
    super();
  }

  public TextType(final Attributes attributes) {
    super(attributes);
  }

  public String output(final int offset) {
    return StringHelper.whitespace(offset) + "<text> " + super.output(offset);
  }

  public ElementRenderer[] createElementRenderer(final Nifty nifty) {
    TextRenderer textRenderer = new TextRenderer();
    ElementRenderer[] panelRenderer = NiftyFactory.getPanelRenderer();
    ElementRenderer[] renderer = new ElementRenderer[panelRenderer.length + 1];
    for (int i = 0; i < panelRenderer.length; i++) {
      renderer[i] = panelRenderer[i];
    }
    renderer[panelRenderer.length] = textRenderer;
    return renderer;
  }
}