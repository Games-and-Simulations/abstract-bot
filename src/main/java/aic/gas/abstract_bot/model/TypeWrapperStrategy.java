package aic.gas.abstract_bot.model;

import aic.gas.abstract_bot.model.game.wrappers.AUnitTypeWrapper;

/**
 * Template for strategy to get define strategy to obtain type after game is loaded
 */
public interface TypeWrapperStrategy {

  AUnitTypeWrapper returnType();
}
