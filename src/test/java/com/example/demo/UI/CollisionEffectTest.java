import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.demo.UI.CollisionEffect;
import static org.junit.jupiter.api.Assertions.*;

public class CollisionEffectTest {

    private Group root;
    private CollisionEffect collisionEffect;
    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }
    @BeforeEach
    public void setUp() {
        // Set up the root Group and CollisionEffect instance
        root = new Group();
        collisionEffect = new CollisionEffect(root);
    }

    @Test
    public void testDisplayEffect() {
        // Given: The coordinates for the collision effect
        double x = 100.0;
        double y = 150.0;

        // Run the collision effect on the JavaFX application thread
        Platform.runLater(() -> collisionEffect.displayEffect(x, y));

        // Sleep for a short duration to allow the effect to be processed
        try {
            Thread.sleep(100); // Allow some time for the effect to render
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check that the root has the collision image added
        assertEquals(1, root.getChildren().size(), "The root should contain one child (the image)");

        // Verify that the image is centered correctly at the given (x, y)
        ImageView imageView = (ImageView) root.getChildren().get(0);
        assertNotNull(imageView, "The image view should not be null.");
        assertEquals(x - imageView.getFitWidth() / 2, imageView.getX(), "The image X position should be correct.");
        assertEquals(y - imageView.getFitHeight() / 2, imageView.getY(), "The image Y position should be correct.");

        // Ensure that the fade transition is properly set up
        FadeTransition fadeTransition = (FadeTransition) imageView.getProperties().get("fadeTransition");

        assertNotNull(fadeTransition, "The fade transition should not be null.");
        assertEquals(1.0, fadeTransition.getFromValue(), "The fade transition should start fully opaque.");
        assertEquals(0.0, fadeTransition.getToValue(), "The fade transition should end fully transparent.");

        // Wait for a little longer than the fade transition duration
        try {
            Thread.sleep(2100); // Allow time for the fade-out to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // After the fade transition, check if the image has been removed from the root
        assertEquals(0, root.getChildren().size(), "The root should be empty after the fade-out.");
    }
}
