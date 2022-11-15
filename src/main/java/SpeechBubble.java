import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class SpeechBubble {

    public SpeechBubble() {

    }

    private static class LeftArrowBubble extends JPanel { // 왼쪽 말풍선
        private int radius = 10;
        private int arrowSize = 12;
        private int strokeThickness = 3;
        private int padding = strokeThickness / 2;
        public void paintComponent(final Graphics g) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0.5f, 0.8f, 1f));
            int x = padding + strokeThickness + arrowSize;
            int width = getWidth() - arrowSize - (strokeThickness * 2);
            int bottomLineY = getHeight() - strokeThickness;
            g2d.fillRect(x, padding, width, bottomLineY);
            g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON));
            g2d.setStroke(new BasicStroke(strokeThickness));
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding, width, bottomLineY, radius, radius);
            Polygon arrow = new Polygon();
            arrow.addPoint(20, 8);
            arrow.addPoint(0, 10);
            arrow.addPoint(20, 12);
            Area area = new Area(rect);
            area.add(new Area(arrow));
            g2d.draw(area);
        }
    }

    private static class RightArrowBubble extends JPanel { // 오른쪽 말풍선

    }
}
