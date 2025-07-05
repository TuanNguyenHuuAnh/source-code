/*******************************************************************************
 * Class        ：LocationTextExtractionStrategyWithPosition
 * Created date ：2020/05/29
 * Lasted date  ：2020/05/29
 * Author       ：HungHT
 * Change log   ：2020/05/29：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

/**
 * <p>
 * LocationTextExtractionStrategyWithPosition
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class LocationTextExtractionStrategyWithPosition extends LocationTextExtractionStrategy {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(LocationTextExtractionStrategyWithPosition.class);

    /**
     * <p>
     * Instantiates a new location text extraction strategy with position.
     * </p>
     *
     * @param pattern
     *            type {@link Pattern}
     * @author tantm
     */
    public LocationTextExtractionStrategyWithPosition(Pattern pattern) {
        super(new TextChunkLocationStrategy() {

            public TextChunkLocation createLocation(TextRenderInfo renderInfo, LineSegment baseline) {
                // while baseLine has been changed to not neutralize
                // effects of rise, ascentLine and descentLine explicitly
                // have not: We want the actual positions.
                return new AscentDescentTextChunkLocation(baseline, renderInfo.getAscentLine(), renderInfo.getDescentLine(),
                        renderInfo.getSingleSpaceWidth());
            }
        });
        this.pattern = pattern;
    }

    /** The locational result field. */
    static Field locationalResultField = null;
    
    /** The filter text chunks method. */
    static Method filterTextChunksMethod = null;
    
    /** The starts with space method. */
    static Method startsWithSpaceMethod = null;
    
    /** The ends with space method. */
    static Method endsWithSpaceMethod = null;
    
    /** The text chunk text field. */
    static Field textChunkTextField = null;
    
    /** The text chunk same line method. */
    static Method textChunkSameLineMethod = null;
    static {
        try {
            locationalResultField = LocationTextExtractionStrategy.class.getDeclaredField("locationalResult");
            locationalResultField.setAccessible(true);
            filterTextChunksMethod = LocationTextExtractionStrategy.class.getDeclaredMethod("filterTextChunks", List.class,
                    TextChunkFilter.class);
            filterTextChunksMethod.setAccessible(true);
            startsWithSpaceMethod = LocationTextExtractionStrategy.class.getDeclaredMethod("startsWithSpace", String.class);
            startsWithSpaceMethod.setAccessible(true);
            endsWithSpaceMethod = LocationTextExtractionStrategy.class.getDeclaredMethod("endsWithSpace", String.class);
            endsWithSpaceMethod.setAccessible(true);
            textChunkTextField = TextChunk.class.getDeclaredField("text");
            textChunkTextField.setAccessible(true);
            textChunkSameLineMethod = TextChunk.class.getDeclaredMethod("sameLine", TextChunk.class);
            textChunkSameLineMethod.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException | NoSuchMethodException e) {
            logger.error("[LocationTextExtractionStrategy] Get supper method failed.", e);
        }
    }

    /**
     * <p>
     * Get locations BK.
     * </p>
     *
     * @param chunkFilter
     *            type {@link TextChunkFilter}
     * @return {@link Collection<TextRectangle>}
     * @author tantm
     */
    @SuppressWarnings("unchecked")
    public Collection<TextRectangle> getLocationsBK(TextChunkFilter chunkFilter) {
        Collection<TextRectangle> result = new ArrayList<>();
        try {
            List<TextChunk> filteredTextChunks = (List<TextChunk>) filterTextChunksMethod.invoke(this, locationalResultField.get(this),
                    chunkFilter);
            Collections.sort(filteredTextChunks);

            StringBuilder sb = new StringBuilder();
            List<AscentDescentTextChunkLocation> locations = new ArrayList<>();
            TextChunk lastChunk = null;
            for (TextChunk chunk : filteredTextChunks) {
                String chunkText = (String) textChunkTextField.get(chunk);
                if (lastChunk == null) {
                    // Nothing to compare with at the end
                } else if ((boolean) textChunkSameLineMethod.invoke(chunk, lastChunk)) {
                    // we only insert a blank space if the trailing character of the previous string
                    // wasn't a space,
                    // and the leading character of the current string isn't a space
                    if (isChunkAtWordBoundary(chunk, lastChunk) && !((boolean) startsWithSpaceMethod.invoke(this, chunkText))
                            && !((boolean) endsWithSpaceMethod.invoke(this, chunkText))) {
                        sb.append(' ');
                        LineSegment spaceBaseLine = new LineSegment(lastChunk.getEndLocation(), chunk.getStartLocation());
                        locations.add(
                                new AscentDescentTextChunkLocation(spaceBaseLine, spaceBaseLine, spaceBaseLine, chunk.getCharSpaceWidth()));
                    }
                } else {
                    assert sb.length() == locations.size();
                    Matcher matcher = pattern.matcher(sb);
                    while (matcher.find()) {
                        int i = matcher.start();
                        Vector baseStart = locations.get(i).getStartLocation();
                        TextRectangle textRectangle = new TextRectangle(matcher.group(), baseStart.get(Vector.I1),
                                baseStart.get(Vector.I2));
                        for (; i < matcher.end(); i++) {
                            AscentDescentTextChunkLocation location = locations.get(i);
                            textRectangle.add(location.getAscentLine().getBoundingRectange());
                            textRectangle.add(location.getDescentLine().getBoundingRectange());
                        }

                        result.add(textRectangle);
                    }

                    sb.setLength(0);
                    locations.clear();
                }
                sb.append(chunkText);
                locations.add((AscentDescentTextChunkLocation) chunk.getLocation());
                lastChunk = chunk;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("[getLocations] Find key search failed.", e);
        }
        return result;
    }

    /**
     * <p>
     * Get locations.
     * </p>
     *
     * @param keySearch
     *            type {@link String}
     * @return {@link List<TextLocation>}
     * @author tantm
     */
    @SuppressWarnings("unchecked")
    public List<TextLocation> getLocations(String keySearch) {
        List<TextLocation> result = new ArrayList<>();
        char firstChar = keySearch.charAt(0);
        int keySearchLength = keySearch.length();
        String sb = "";
        boolean matchFirstChar = false;
        TextChunk matchFirstChunk = null;
        try {
            List<TextChunk> filteredTextChunks = (List<TextChunk>) filterTextChunksMethod.invoke(this, locationalResultField.get(this),
                    null);
            Collections.sort(filteredTextChunks);

            TextChunk lastChunk = null;
            for (TextChunk chunk : filteredTextChunks) {
                String chunkText = (String) textChunkTextField.get(chunk);
                if (lastChunk == null) {
                    // initial
                    if (firstChar == chunkText.charAt(0)) {
                        sb += chunkText;
                        matchFirstChunk = chunk;
                        matchFirstChar = true;
                    }
                } else if ((boolean) textChunkSameLineMethod.invoke(chunk, lastChunk)) {
                    if (!matchFirstChar && firstChar == chunkText.charAt(0)) {
                        sb += chunkText;
                        matchFirstChunk = chunk;
                        matchFirstChar = true;
                        continue;
                    }

                    if (matchFirstChar) {
                        sb += chunkText;
                        if (keySearch.indexOf(sb) == -1 || sb.length() > keySearchLength) {
                            matchFirstChunk = null;
                            matchFirstChar = false;
                            sb = "";
                            continue;
                        }

                        if (sb.length() == keySearchLength && sb.equals(keySearch)) {
                            result.add(new TextLocation(matchFirstChunk.getLocation().getStartLocation().get(0),
                                    matchFirstChunk.getLocation().getStartLocation().get(1), sb.toString()));
                            return result;
                        }
                    }
                } else {
                    if (firstChar == chunkText.charAt(0)) {
                        sb += chunkText;
                        matchFirstChunk = chunk;
                        matchFirstChar = true;
                    }
                }
                lastChunk = chunk;
            }
        } catch (Exception e) {
            logger.error("[getLocations] Find key search failed.", e);
        }
        return result;
    }

    /**
     * <p>
     * Calc coordinate of key search.
     * </p>
     *
     * @param keySearch
     *            type {@link String}
     * @return {@link Coordinate}
     * @author tantm
     */
    public Coordinate calcCoordinateOfKeySearch(String keySearch) {
        Coordinate result = new Coordinate(0f, 0f, 0f, 0f);
        final float SIGN_APPEARANCE_HEIGHT = 155;
        final float SIGN_APPEARANCE_WIDTH = 155;
        final float PADDING_LEFT = 55;
        final float PADDING_BOTTOM = 8;

        List<TextLocation> locations = this.getLocations(keySearch);
        if (!locations.isEmpty()) {
            result = new Coordinate(locations.get(0).getX() - PADDING_LEFT, locations.get(0).getY() - PADDING_BOTTOM,
                    locations.get(0).getX() - PADDING_LEFT + SIGN_APPEARANCE_WIDTH,
                    locations.get(0).getY() - PADDING_BOTTOM + SIGN_APPEARANCE_HEIGHT);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy#renderText(com.itextpdf.text.pdf.parser.TextRenderInfo)
     */
    @Override
    public void renderText(TextRenderInfo renderInfo) {
        for (TextRenderInfo info : renderInfo.getCharacterRenderInfos())
            super.renderText(info);
    }

    /**
     * <p>
     * AscentDescentTextChunkLocation
     * </p>
     * .
     *
     * @version 01-00
     * @since 01-00
     * @author tantm
     */
    public static class AscentDescentTextChunkLocation extends TextChunkLocationDefaultImp {

        /**
         * <p>
         * Instantiates a new ascent descent text chunk location.
         * </p>
         *
         * @param baseLine
         *            type {@link LineSegment}
         * @param ascentLine
         *            type {@link LineSegment}
         * @param descentLine
         *            type {@link LineSegment}
         * @param charSpaceWidth
         *            type {@link float}
         * @author tantm
         */
        public AscentDescentTextChunkLocation(LineSegment baseLine, LineSegment ascentLine, LineSegment descentLine, float charSpaceWidth) {
            super(baseLine.getStartPoint(), baseLine.getEndPoint(), charSpaceWidth);
            this.ascentLine = ascentLine;
            this.descentLine = descentLine;
        }

        /**
         * <p>
         * Get ascent line.
         * </p>
         *
         * @return {@link LineSegment}
         * @author tantm
         */
        public LineSegment getAscentLine() {
            return ascentLine;
        }

        /**
         * <p>
         * Get descent line.
         * </p>
         *
         * @return {@link LineSegment}
         * @author tantm
         */
        public LineSegment getDescentLine() {
            return descentLine;
        }

        /** The ascent line. */
        final LineSegment ascentLine;
        
        /** The descent line. */
        final LineSegment descentLine;
    }

    /**
     * <p>
     * TextRectangle
     * </p>
     * .
     *
     * @version 01-00
     * @since 01-00
     * @author tantm
     */
    public class TextRectangle extends Rectangle2D.Float {

        /**
         * <p>
         * Instantiates a new text rectangle.
         * </p>
         *
         * @param text
         *            type {@link String}
         * @param xStart
         *            type {@link float}
         * @param yStart
         *            type {@link float}
         * @author tantm
         */
        public TextRectangle(final String text, final float xStart, final float yStart) {
            super(xStart, yStart, 0, 0);
            this.text = text;
        }

        /**
         * <p>
         * Get text.
         * </p>
         *
         * @return {@link String}
         * @author tantm
         */
        public String getText() {
            return text;
        }

        /** The text. */
        final String text;
    }

    /**
     * <p>
     * TextLocation
     * </p>
     * .
     *
     * @version 01-00
     * @since 01-00
     * @author tantm
     */
    public class TextLocation {

        /** The x. */
        private float X;
        
        /** The y. */
        private float Y;
        
        /** The Text. */
        private String Text;

        /**
         * <p>
         * Instantiates a new text location.
         * </p>
         *
         * @param x
         *            type {@link float}
         * @param y
         *            type {@link float}
         * @param text
         *            type {@link String}
         * @author tantm
         */
        public TextLocation(float x, float y, String text) {
            super();
            X = x;
            Y = y;
            Text = text;
        }

        /**
         * <p>
         * Get x.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getX() {
            return X;
        }

        /**
         * <p>
         * Sets the x.
         * </p>
         *
         * @param x
         *            the new x
         * @author tantm
         */
        public void setX(float x) {
            X = x;
        }

        /**
         * <p>
         * Get y.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getY() {
            return Y;
        }

        /**
         * <p>
         * Sets the y.
         * </p>
         *
         * @param y
         *            the new y
         * @author tantm
         */
        public void setY(float y) {
            Y = y;
        }

        /**
         * <p>
         * Get text.
         * </p>
         *
         * @return {@link String}
         * @author tantm
         */
        public String getText() {
            return Text;
        }

        /**
         * <p>
         * Sets the text.
         * </p>
         *
         * @param text
         *            the new text
         * @author tantm
         */
        public void setText(String text) {
            Text = text;
        }

    }

    /**
     * <p>
     * Coordinate
     * </p>
     * .
     *
     * @version 01-00
     * @since 01-00
     * @author tantm
     */
    public class Coordinate {

        /** The llx. */
        private float llx;
        
        /** The lly. */
        private float lly;
        
        /** The urx. */
        private float urx;
        
        /** The ury. */
        private float ury;

        /**
         * <p>
         * Instantiates a new coordinate.
         * </p>
         *
         * @author tantm
         */
        public Coordinate() {
        }

        /**
         * <p>
         * Instantiates a new coordinate.
         * </p>
         *
         * @param llx
         *            type {@link float}
         * @param lly
         *            type {@link float}
         * @param urx
         *            type {@link float}
         * @param ury
         *            type {@link float}
         * @author tantm
         */
        public Coordinate(float llx, float lly, float urx, float ury) {
            super();
            this.llx = llx;
            this.lly = lly;
            this.urx = urx;
            this.ury = ury;
        }

        /**
         * <p>
         * Get llx.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getLlx() {
            return llx;
        }

        /**
         * <p>
         * Sets the llx.
         * </p>
         *
         * @param llx
         *            the new llx
         * @author tantm
         */
        public void setLlx(float llx) {
            this.llx = llx;
        }

        /**
         * <p>
         * Get lly.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getLly() {
            return lly;
        }

        /**
         * <p>
         * Sets the lly.
         * </p>
         *
         * @param lly
         *            the new lly
         * @author tantm
         */
        public void setLly(float lly) {
            this.lly = lly;
        }

        /**
         * <p>
         * Get urx.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getUrx() {
            return urx;
        }

        /**
         * <p>
         * Sets the urx.
         * </p>
         *
         * @param urx
         *            the new urx
         * @author tantm
         */
        public void setUrx(float urx) {
            this.urx = urx;
        }

        /**
         * <p>
         * Get ury.
         * </p>
         *
         * @return {@link float}
         * @author tantm
         */
        public float getUry() {
            return ury;
        }

        /**
         * <p>
         * Sets the ury.
         * </p>
         *
         * @param ury
         *            the new ury
         * @author tantm
         */
        public void setUry(float ury) {
            this.ury = ury;
        }

        /**
         * <p>
         * Get rectangle location.
         * </p>
         *
         * @return {@link String}
         * @author tantm
         */
        public String getRectangleLocation() {
            if (this.llx == 0.0f && this.lly == 0.0f && this.ury == 0.0f && this.ury == 0.0f) {
                this.llx = 400;
                this.lly = 700;
                this.urx = 500;
                this.ury = 800;
            }
            return String.valueOf(Math.round(llx)).concat(",").concat(String.valueOf(Math.round(lly)).concat(","))
                    .concat(String.valueOf(Math.round(urx)).concat(",")).concat(String.valueOf(Math.round(ury)));
        }
    }

    /** The pattern. */
    final Pattern pattern;

    /**
     * <p>
     * The main method.
     * </p>
     *
     * @param args
     *            the arguments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static void main(String[] args) throws IOException {
        byte[] byteArray = Files.readAllBytes(Paths.get("PDF.pdf"));
        PdfReader pdfReader = new PdfReader(byteArray);
        Coordinate result = null;
        try {
            PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
            for (int pageNr = pdfReader.getNumberOfPages(); pageNr > 0; pageNr--) {
                LocationTextExtractionStrategyWithPosition strategy = new LocationTextExtractionStrategyWithPosition(null);
                parser.processContent(pageNr, strategy, Collections.emptyMap()).getResultantText();
                result = strategy.calcCoordinateOfKeySearch("26-03-2020 10:32:45");
                if (null != result) {
                    break;
                }
            }
            System.out.println(result.getRectangleLocation());
        } finally {
            pdfReader.close();
        }
    }
}
