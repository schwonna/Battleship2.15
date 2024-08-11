package battleship.view;

/**
 * A separate thread for loading GIF images in the background.
 * It is used to initialize the GIF in the View class without causing delays in the setup method.
 */
public class GifThread extends Thread{
    private View parent;

    /**
     * Constructs a GifThread with the specified parent view.
     *
     * @param parent The parent view to associate with this thread.
     */
    GifThread(View parent){this.parent = parent;}

    /**
     * Runs the thread to load GIF images in the background.
     */
    @Override
    public void run(){
        parent.firstLoadImages();
    }



}
