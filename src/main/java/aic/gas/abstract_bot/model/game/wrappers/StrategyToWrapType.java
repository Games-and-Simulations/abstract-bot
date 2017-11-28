package aic.gas.abstract_bot.model.game.wrappers;

/**
 * Contract for strategy to wrap type
 */
interface StrategyToWrapType<T, V extends AbstractWrapper<T>> {

  /**
   * Wrap type
   */
  V createNewWrapper(T type);

}
