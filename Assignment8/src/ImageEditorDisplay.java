import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageEditorDisplay {
    // Constants
    final static Dimension IMAGE_DIMENSIONS = new Dimension(500, 500);

    public static void main(String[] args) {
        JFrame frame = getFrame(); // keep the code for making the frame organized into methods
        frame.setVisible(true);
    }

    private static JFrame getFrame() {
        // Basic frame properties
        // TODO: frame with title, default close behavior, dimensions 1200x600

        // mainPanel is the overall panel, which contains 3 panels

        // TODO declare and instantiate a mainPanel JPanel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // formats elements horizontally

        // TODO: Rather than using static, define the elements that need to be referenced in multiple components
        //       here, and pass in references to them to the elements that specifically need them.
        //       For example, the buttons that do the transforming need to know the image name, so they'll need
        //       reference to the inputTextField, which means it needs to be defined here.
        //       Check the parameters for each getXYZPanel method for guidance on what needs to be accessible by reference
        JTextField inputPathField = new JTextField("cake.ppm", 30); // default text and size

        // Modular panel pieces: left, middle, and right
        // TODO: create the left, middle, and right panels by calling the methods that create them. Pass in the needed references

        // Final mainPanel and frame preparations before main() displays it
        // TODO: add everything to the mainPanel/frame appropriately, and remember to pack the frame
        return frame;
    }

    private static JPanel getLeftPanel(JPanel leftImagePanel, JTextField inputPathField, JPanel rightImagePanel) {
        // Left Panel: contains a subpanel for the image, a text field, and a button
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // leftImagePanel is a panel within leftPanel which contains only the component that draws the image
        // when an image needs to change, swap it out. This is why its defined statically in the class
        // Add the component that draws the image, but no constructor args because it doesn't draw an image
        // TODO: instantiate the leftImage using our custom JComponent subclass
        leftImage.setPreferredSize(IMAGE_DIMENSIONS); // this ensures the image isn't too small to see
        leftImagePanel.add(leftImage);

        // Other elements
        // TODO: add a button for selecting an image with the text "Select Image"
        class SelectImageHandler implements ActionListener {
            // Loads the image specified in the inputPathField and replaces the left image with it,
            // throwing an error if the image cannot be found. Clears the right image, too.
            public void actionPerformed(ActionEvent e) {
                BufferedImage newImage;
                try {
                    newImage = EditorHelperMethods.resolveImageFromString(inputPathField.getText());
                } catch (IOException ex) {
                    System.out.println("Error: image '" + inputPathField.getText() + "' could not be found: " + ex.getMessage());
                    return;
                }
                // TODO: use a helper method from this class twice to replace the left image with the new image
                //      and to clear the right image.
            }
        }
        // TODO: give the button the actionlistener above for selecting an image

        // Last steps with the panel
        // TODO
        return leftPanel;
    }

    private static JPanel getMiddlePanel(JPanel rightImagePanel, JTextField inputPathField) {
        // Middle panel has many buttons, one per transformation, and that's it
        // TODO: this one's all on you. Make the buttons, give them appropriate text, and give them action listeners
        //       Throughout this file, define and instantiate the inner classes that implement ActionListener to make our buttons work.
        //       actionPerformed's body for each method should be really short: it just uses the premade method for doing the transform
        //       and passes in the appropriate transform code, rightImagePanel, and inputPathField.
        //       Make sure you don't hardcode the transform code -- use the constants defined in EditorHelperMethods so that
        //       way they always match up.
    }

    private static JPanel getRightPanel(JPanel rightImagePanel, JTextField inputPathField, JPanel leftImagePanel) {
        // Similar to left panel, but has the save button and an extra button that reuses the current output
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        // TODO: instantiate the rightImage using our custom JComponent subclass
        rightImage.setPreferredSize(IMAGE_DIMENSIONS);
        rightImagePanel.add(rightImage);
        JTextField outputPathField = ...; // TODO instantiate the JTextField with NO default text and 30 columns
        // TODO: create save and reuse buttons with appropriate text and event listeners. They are defined below
        class SaveImageHandler implements ActionListener {
            // Saves the output image to a file, based on what the user wants it to be called
            // Behind the scenes, though, we've already saved it in temp.ppm, so we just duplicate that file with the new name
            // That way temp.ppm isn't overwritten and their image is lost. This is done by the EditorHelperMethods method for you.
            public void actionPerformed(ActionEvent e) {
                if (outputPathField.getText().equals("")) {
                    outputPathField.setText("out.ppm"); // default name for saving if one isn't entered
                }
                try {
                    // TODO call the right EditorHelperMethod to save the temporary image to the desired file
                } catch (IOException ex) {
                    System.out.println("Error: image '" + outputPathField.getText() + "' could not be found: " + ex.getMessage());
                }
            }
        }

        class ReuseImageHandler implements ActionListener {
            // This method does:
            // 1. If the output field text isn't a valid file name (i.e. if it doesn't end with .ppm),
            //    puts out.ppm into the input field.
            //    Otherwise, put the output file name into the input field.
            // 2. Saves the temp file with that new name that's now in input field, logging an error if this fails
            // 2. Clears the output path field
            // 3. Gets the output image as a BufferedImage (much like the other methods in this class) logging an error if
            //    this fails, and replaces the left image with it.
            // 4. Replaces the right image with null. This means nothing will be drawn, so the "no image to show" text is visible
            // TODO implement this logic
        }
        // TODO: last steps for this method
    }

    private static void replaceImage(JPanel imagePanel, BufferedImage newImage) {
        // This helper method takes in the imagePanel containing the image component to be modified, and the new image to display.
        // It then removes the component inside the corresponding panel, generates the new one based on newImage and adds it, then
        // redraws the panel so the changes apply visually
        imagePanel.remove(0);
        DrawnImageComponent newImageComponent = new DrawnImageComponent(newImage);
        newImageComponent.setPreferredSize(IMAGE_DIMENSIONS);
        imagePanel.add(newImageComponent);
        imagePanel.revalidate();
    }

    private static void performTransform(int transformCode, JPanel rightImagePanel, JTextField inputPathField) {
        // This helper method avoids duplicate code in the actionPerformed method bodies for each event handler.
        // It just needs to know the transform code we want to use, then will call the method in EditorHelperMethods
        // that is used to transform the image with that given transform code and the filename typed into the input field
        // Lastly, replace the right image with that new image.
        BufferedImage newImage;
        try {
            newImage = EditorHelperMethods.transformImage(inputPathField.getText(), transformCode);
        } catch (IOException ex) {
            System.out.println("Error: image '" + inputPathField.getText() + "' could not be found: " + ex.getMessage());
            return;
        }
        replaceImage(rightImagePanel, newImage);
    }
}
