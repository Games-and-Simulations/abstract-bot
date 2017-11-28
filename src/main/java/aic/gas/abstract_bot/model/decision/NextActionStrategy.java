package aic.gas.abstract_bot.model.decision;

/**
 * Contract to return commitment associated with strategy implementing this interface.
 */
interface NextActionStrategy {

  /**
   * Return next commitment
   */
  boolean commit();

}
