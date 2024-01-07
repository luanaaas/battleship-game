package cs3500.pa04.model;

import cs3500.pa04.view.GameView;
import java.io.IOException;
import java.util.List;

/**
 * Represents a manual shots data model
 */
public class ManualDataModel {

  private GameView view;

  /**
   * Represents the constructor for a manual shots data model
   *
   * @param view the salvo game view
   */
  public ManualDataModel(GameView view)  {
    this.view = view;
  }


  /**
   * @param shotsAllowed the amount of shots the player is allowed to fire
   * @return a list of shots that the user can take
   * @throws IOException if not the proper output append for view
   */
  public List<Coord> shots(int shotsAllowed) throws IOException {
    return view.promptShots(shotsAllowed);
  }
}

