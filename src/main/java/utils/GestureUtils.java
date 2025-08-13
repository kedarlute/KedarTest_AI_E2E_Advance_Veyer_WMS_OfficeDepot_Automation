package com.example.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.LongPressOptions;

import java.time.Duration;

public class GestureUtils {

    private AndroidDriver<MobileElement> driver;

    public GestureUtils(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    // Scroll to element by text
    public void scrollToText(String visibleText) {
        driver.findElementByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().text(\"" + visibleText + "\"));"
        );
    }

    // Swipe from one point to another
    public void swipe(int startX, int startY, int endX, int endY) {
        new TouchAction<>(driver)
            .press(PointOption.point(startX, startY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
            .moveTo(PointOption.point(endX, endY))
            .release()
            .perform();
    }

    // Long press on an element
    public void longPress(MobileElement element, int seconds) {
        new TouchAction<>(driver)
            .longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(element))
                .withDuration(Duration.ofSeconds(seconds)))
            .release()
            .perform();
    }

    // Pinch gesture (fingers move together)
    public void pinch(int centerX, int centerY) {
        TouchAction<?> finger1 = new TouchAction<>(driver)
            .press(PointOption.point(centerX - 100, centerY - 100))
            .moveTo(PointOption.point(centerX, centerY))
            .release();

        TouchAction<?> finger2 = new TouchAction<>(driver)
            .press(PointOption.point(centerX + 100, centerY + 100))
            .moveTo(PointOption.point(centerX, centerY))
            .release();

        new MultiTouchAction(driver)
            .add(finger1)
            .add(finger2)
            .perform();
    }

    // Zoom gesture (fingers move apart)
    public void zoom(int centerX, int centerY) {
        TouchAction<?> finger1 = new TouchAction<>(driver)
            .press(PointOption.point(centerX, centerY))
            .moveTo(PointOption.point(centerX - 100, centerY - 100))
            .release();

        TouchAction<?> finger2 = new TouchAction<>(driver)
            .press(PointOption.point(centerX, centerY))
            .moveTo(PointOption.point(centerX + 100, centerY + 100))
            .release();

        new MultiTouchAction(driver)
            .add(finger1)
            .add(finger2)
            .perform();
    }
}
