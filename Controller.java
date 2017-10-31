package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    public ListView<Object> sd;
    private StandardHuffman standardHuffman = new StandardHuffman();
    private File file;


    public void choose() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("D:\\"));
        file = fc.showOpenDialog(null);
    }

    public void compress() {
        StandardHuffman.root = StandardHuffman.compress(file.getAbsolutePath());
        sd.getItems().remove(0, sd.getItems().size());
        sd.getItems().add(StandardHuffman.bits);
        sd.getItems().add(StandardHuffman.lookUpTable);
    }

    public void decompress( ) {
        sd.getItems().remove(0, sd.getItems().size());
        sd.getItems().add(StandardHuffman.decompress(StandardHuffman.root));
    }
}
