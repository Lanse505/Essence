package com.teamacronymcoders.essence.container.widget;

import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;

public abstract class BaseScrollableScreenAddon extends BasicScreenAddon {

  protected double scroll;
  private boolean isDragging;
  private int dragOffset;
  protected final int barX;
  protected final int barY;
  protected final int barWidth;
  protected final int barHeight;
  protected final int maxBarHeight;

  protected BaseScrollableScreenAddon(int posX, int posY, int barXShift, int barYShift, int barWidth, int barHeight, int maxBarHeight) {
    super(posX, posY);
    this.barX = this.getXSize() + barXShift;
    this.barY = this.getYSize() + barYShift;
    this.barWidth = barWidth;
    this.barHeight = barHeight;
    this.maxBarHeight = maxBarHeight;
  }

  protected abstract int getMaxElements();

  protected abstract int getFocusedElements();

  public void onClick(double mouseX, double mouseY) {
//        int scroll  = getScroll();
//        if (mouseX >= barX && mouseX <= barX + barWidth && mouseY >= barY + scroll && mouseY <= barY + scroll + barHeight) {
//            if (needsScrollBars()) {
//                double yAxis = mouseY - guiObj.getTop();
//                dragOffset = (int) (yAxis - (scroll + barY));
//                //Mark that we are dragging so that we can continue to "drag" even if our mouse goes off of being over the element
//                isDragging = true;
//            } else {
//                this.scroll = 0;
//            }
//        }
  }

  protected boolean needsScrollBars() {
    return getMaxElements() > getFocusedElements();
  }

  private int getMax() {
    return maxBarHeight - barHeight;
  }

  protected int getScroll() {
    int max = getMax();
    return Math.max(Math.min((int) (scroll * max), max), 0);
  }

}
