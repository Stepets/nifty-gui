package de.lessvoid.niftyimpl.layout.manager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lessvoid.nifty.layout.SizeValue;
import de.lessvoid.nifty.layout.VerticalAlign;
import de.lessvoid.niftyimpl.layout.Box;
import de.lessvoid.niftyimpl.layout.BoxConstraints;
import de.lessvoid.niftyimpl.layout.Layoutable;

public class HorizontalLayoutTest {
  private HorizontalLayout layout = new HorizontalLayout();
  private LayoutPartTestHelper rootPanel;
  private List<Layoutable> elements;
  private LayoutPartTestHelper left;
  private LayoutPartTestHelper right;

  @Before
  public void setUp() throws Exception {
    rootPanel = new LayoutPartTestHelper(new Box(0, 0, 640, 480), new BoxConstraints());
    left = new LayoutPartTestHelper(new Box(), new BoxConstraints());
    right = new LayoutPartTestHelper(new Box(), new BoxConstraints());

    elements = new ArrayList<Layoutable>();
    elements.add(left);
    elements.add(right);
  }

  @Test
  public void testUpdateEmpty() throws Exception {
    layout.layoutElements(null, null);
  }

  @Test
  public void testUpdateWithNullEntriesMakeNoTrouble() {
    layout.layoutElements(rootPanel, null);
  }

  @Test
  public void testUpdateWithEmptyChildren() {
    layout.layoutElements(rootPanel, new ArrayList<Layoutable>());
  }

  @Test
  public void testLayoutDefault() {
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 320, 480);
    Assert.assertBox(right.getLayoutPos(), 320, 0, 320, 480);
  }

  @Test
  public void testLayoutFixedHeight() {
    left.getBoxConstraints().setHeight(new SizeValue("20px"));
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 320, 20);
    Assert.assertBox(right.getLayoutPos(), 320, 0, 320, 480);
  }

  @Test
  public void testLayoutMaxHeight() {
    left.getBoxConstraints().setHeight(new SizeValue("100%"));
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 320, 480);
    Assert.assertBox(right.getLayoutPos(), 320, 0, 320, 480);
  }

  @Test
  public void testLayoutMaxHeightWildcard() {
    left.getBoxConstraints().setHeight(new SizeValue("*"));
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 320, 480);
    Assert.assertBox(right.getLayoutPos(), 320, 0, 320, 480);
  }

  @Test
  public void testLayoutFixedWidth() {
    left.getBoxConstraints().setWidth(new SizeValue("20px"));
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 20, 480);
  }

  @Test
  public void testLayoutFixedWidthTopAlign() {
    left.getBoxConstraints().setWidth(new SizeValue("20px"));
    left.getBoxConstraints().setVerticalAlign(VerticalAlign.top);
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 20, 480);
  }

  @Test
  public void testLayoutFixedHeightCenterAlign() {
    left.getBoxConstraints().setHeight(new SizeValue("20px"));
    left.getBoxConstraints().setVerticalAlign(VerticalAlign.center);
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 230, 320, 20);
  }

  @Test
  public void testLayoutFixedHeightBottomAlign() {
    left.getBoxConstraints().setHeight(new SizeValue("20px"));
    left.getBoxConstraints().setVerticalAlign(VerticalAlign.bottom);
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 460, 320, 20);
  }

  @Test
  public void testLayoutWithPercentage() throws Exception {
    left.getBoxConstraints().setWidth(new SizeValue("25%"));
    right.getBoxConstraints().setWidth(new SizeValue("75%"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 160, 480);
    Assert.assertBox(right.getLayoutPos(), 160, 0, 480, 480);
  }

  @Test
  public void testLayoutWithMixedFixedAndPercentage() throws Exception {
    left.getBoxConstraints().setWidth(new SizeValue("40px"));
    right.getBoxConstraints().setWidth(new SizeValue("*"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 0, 0, 40, 480);
    Assert.assertBox(right.getLayoutPos(), 40, 0, 600, 480);
  }

  @Test
  public void testLayoutDefaultWithAllEqualPadding() {
    rootPanel.getBoxConstraints().setPadding(new SizeValue("10px"));
    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(), 10, 10, 310, 460);
    Assert.assertBox(right.getLayoutPos(), 320, 10, 310, 460);
  }

  @Test
  public void testLayoutWithWidthSuffixLeft() {
    left.getBoxConstraints().setWidth(new SizeValue("50%h"));
    left.getBoxConstraints().setHeight(new SizeValue("200px"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(),    0, 0, 100, 200);
    Assert.assertBox(right.getLayoutPos(), 100, 0, 540, 480);
  }

  @Test
  public void testLayoutWithWidthSuffixRight() {
    right.getBoxConstraints().setWidth(new SizeValue("50%h"));
    right.getBoxConstraints().setHeight(new SizeValue("200px"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(),    0, 0, 540, 480);
    Assert.assertBox(right.getLayoutPos(), 540, 0, 100, 200);
  }

  @Test
  public void testLayoutWithHeightSuffixLeft() {
    left.getBoxConstraints().setWidth(new SizeValue("200px"));
    left.getBoxConstraints().setHeight(new SizeValue("50%w"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(),    0, 0, 200, 100);
    Assert.assertBox(right.getLayoutPos(), 200, 0, 440, 480);
  }

  @Test
  public void testLayoutWithHeightSuffixRight() {
    right.getBoxConstraints().setWidth(new SizeValue("200px"));
    right.getBoxConstraints().setHeight(new SizeValue("50%w"));

    layout.layoutElements(rootPanel, elements);

    Assert.assertBox(left.getLayoutPos(),    0, 0, 440, 480);
    Assert.assertBox(right.getLayoutPos(), 440, 0, 200, 100);
  }
}