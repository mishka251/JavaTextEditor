import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;

/**
 * Воспомогательный класс для копирования через буфер
 */
class TransferableText implements Transferable {
    public static final DataFlavor HTML_FLAVOR = new DataFlavor(String.class, "TransferableData");

    private String text;
    private DataFlavor[] flavors = {
            HTML_FLAVOR,
    };

    public TransferableText(String text) {
        this.text = text;
    }

    @Override
    public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(HTML_FLAVOR)) {
            return text;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return Arrays.asList(flavors).contains(flavor);
    }
}