package aic.gas.abstract_bot.model.bot;

import static aic.gas.abstract_bot.model.game.wrappers.AUnitTypeWrapper.CREEP_COLONY_TYPE;
import static aic.gas.abstract_bot.model.game.wrappers.AUnitTypeWrapper.OVERLORD_TYPE;

import aic.gas.abstract_bot.model.UnitTypeStatus;
import aic.gas.abstract_bot.model.game.wrappers.ABaseLocationWrapper;
import aic.gas.abstract_bot.model.game.wrappers.ARace;
import aic.gas.abstract_bot.model.game.wrappers.AUnit;
import aic.gas.abstract_bot.model.game.wrappers.AUnitOfPlayer;
import aic.gas.abstract_bot.model.game.wrappers.AUnitTypeWrapper;
import aic.gas.abstract_bot.model.game.wrappers.AUnitWithCommands;
import aic.gas.mas.model.metadata.FactConverterID;
import aic.gas.mas.model.metadata.containers.FactWithOptionalValue;
import aic.gas.mas.model.metadata.containers.FactWithOptionalValueSet;
import aic.gas.mas.model.metadata.containers.FactWithOptionalValueSetsForAgentType;
import aic.gas.mas.model.metadata.containers.FactWithSetOfOptionalValues;
import aic.gas.mas.model.metadata.containers.FactWithSetOfOptionalValuesForAgentType;
import bwapi.Order;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enumeration of all IDs for facts' types as static classes
 */
public class FactConverters {

  //converters for base
  public static final FactWithSetOfOptionalValues<AUnitOfPlayer> COUNT_OF_WORKERS = new FactWithSetOfOptionalValues<>(
      new FactConverterID<>(1, FactKeys.REPRESENTS_UNIT), optionalStream -> (double) optionalStream
      .filter(Optional::isPresent)
      .map(Optional::get)
      .filter(aUnitOfPlayer -> aUnitOfPlayer.getType().isWorker()
          || (aUnitOfPlayer.getType().isLarvaOrEgg()
          && !aUnitOfPlayer.getTrainingQueue().isEmpty()
          && aUnitOfPlayer.getTrainingQueue().get(0).isWorker()))
      .count());
  public static final FactWithOptionalValueSetsForAgentType<AUnitOfPlayer> AVERAGE_COUNT_OF_WORKERS_PER_BASE = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(2, FactKeys.WORKER_ON_BASE), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToLong(Stream::count)
          .average().orElse(0.0)
  );
  public static final FactWithOptionalValueSetsForAgentType<AUnitOfPlayer> AVERAGE_COUNT_OF_WORKERS_MINING_MINERALS_PER_BASE = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(3, FactKeys.WORKER_MINING_MINERALS), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToLong(Stream::count)
          .average().orElse(0.0)
  );
  public static final FactWithOptionalValueSetsForAgentType<AUnitOfPlayer> AVERAGE_COUNT_OF_WORKERS_MINING_GAS_PER_BASE = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(4, FactKeys.WORKER_MINING_GAS), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToLong(Stream::count)
          .average().orElse(0.0)
  );
  public static final FactWithOptionalValueSetsForAgentType<AUnitOfPlayer> COUNT_OF_EXTRACTORS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(5, FactKeys.HAS_EXTRACTOR), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToLong(Stream::count)
          .sum()
  );
  public static final FactWithOptionalValueSet<AUnit> COUNT_OF_MINERALS_ON_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(6, FactKeys.MINERAL),
      aUnitStream -> aUnitStream.map(aUnitStream1 -> (double) aUnitStream1.count()).orElse(0.0));
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_EXTRACTORS_ON_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(7, FactKeys.HAS_EXTRACTOR),
      aUnitStream -> aUnitStream.map(aUnitStream1 -> (double) aUnitStream1
          .filter(
              aUnitOfPlayer -> !aUnitOfPlayer.isMorphing() && !aUnitOfPlayer.isBeingConstructed())
          .count()).orElse(0.0));
  public static final FactWithOptionalValueSetsForAgentType<AUnit> COUNT_OF_MINERALS_TO_MINE = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(8, FactKeys.MINERAL), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToLong(Stream::count)
          .sum()
  );

  //converters for player's - aggregated data
  public static final FactWithSetOfOptionalValuesForAgentType<Boolean> COUNT_OF_BASES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(11, FactKeys.IS_BASE), optionalStream -> (double) optionalStream
      .filter(Optional::isPresent)
      .filter(Optional::get)
      .count(), AgentTypes.BASE_LOCATION);
  public static final FactWithSetOfOptionalValuesForAgentType<Boolean> COUNT_OF_ENEMY_BASES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(12, FactKeys.IS_ENEMY_BASE), optionalStream -> (double) optionalStream
      .filter(Optional::isPresent)
      .filter(Optional::get)
      .count(), AgentTypes.BASE_LOCATION);
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_AIR_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(13, FactKeys.ENEMY_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_GROUND_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(14, FactKeys.ENEMY_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_AIR_HP = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(15, FactKeys.ENEMY_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value
              .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints()).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_GROUND_HP = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(16, FactKeys.ENEMY_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value
              .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints()).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_AIR_SHIELDS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(17, FactKeys.ENEMY_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(
              value -> value.mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxShields())
                  .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_GROUND_SHIELDS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(18, FactKeys.ENEMY_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(
              value -> value.mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxShields())
                  .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_STATIC_AIR_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(19, FactKeys.ENEMY_STATIC_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_STATIC_GROUND_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(20, FactKeys.ENEMY_STATIC_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_STATIC_AIR_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(21, FactKeys.ENEMY_STATIC_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_STATIC_GROUND_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(22, FactKeys.ENEMY_STATIC_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_AIR_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(23, FactKeys.ENEMY_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_ENEMY_GROUND_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(24, FactKeys.ENEMY_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_AIR_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(25, FactKeys.OWN_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_GROUND_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(26, FactKeys.OWN_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_AIR_HP = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(27, FactKeys.OWN_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value
              .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints()).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_GROUND_HP = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(28, FactKeys.OWN_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value
              .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints()).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_STATIC_AIR_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(29, FactKeys.OWN_STATIC_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_STATIC_GROUND_DMG = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(30, FactKeys.OWN_STATIC_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
              .sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_STATIC_AIR_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(31, FactKeys.OWN_STATIC_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_STATIC_GROUND_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(32, FactKeys.OWN_STATIC_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_AIR_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(33, FactKeys.OWN_AIR_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithOptionalValueSetsForAgentType<UnitTypeStatus> SUM_OF_OWN_GROUND_UNITS = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(34, FactKeys.OWN_GROUND_FORCE_STATUS), AgentTypes.PLAYER,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .mapToDouble(value -> value.mapToDouble(UnitTypeStatus::getCount).sum())
          .sum()
  );
  public static final FactWithSetOfOptionalValuesForAgentType<Boolean> MAP_SIZE = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(35, FactKeys.IS_START_LOCATION),
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .filter(Optional::get)
          .count(), AgentTypes.BASE_LOCATION);
  public static final FactWithSetOfOptionalValuesForAgentType<ARace> OPPONENTS_RACE = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(36, FactKeys.ENEMY_RACE), optionalStream -> (double) optionalStream
      .filter(Optional::isPresent)
      .map(Optional::get)
      .map(Enum::ordinal)
      .findAny().orElse(4), AgentTypes.PLAYER);
  public static final FactWithSetOfOptionalValuesForAgentType<ABaseLocationWrapper> AVAILABLE_BASES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(37, FactKeys.IS_BASE_LOCATION),
      optionalStream -> (double) optionalStream.count(), AgentTypes.BASE_LOCATION);
  public static final FactWithSetOfOptionalValuesForAgentType<Double> CURRENT_POPULATION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(38, FactKeys.POPULATION),
      optionalStream -> optionalStream.filter(Optional::isPresent)
          .mapToDouble(Optional::get)
          .sum(), AgentTypes.PLAYER);
  public static final FactWithSetOfOptionalValuesForAgentType<Double> MAX_POPULATION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(39, FactKeys.POPULATION_LIMIT),
      optionalStream -> optionalStream.filter(Optional::isPresent)
          .mapToDouble(Optional::get)
          .sum(), AgentTypes.PLAYER);

  //base army stats
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_AIR_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(301, FactKeys.ENEMY_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_GROUND_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(302, FactKeys.ENEMY_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_AIR_HP_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(303, FactKeys.ENEMY_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_GROUND_HP_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(304, FactKeys.ENEMY_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_AIR_SHIELDS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(305, FactKeys.ENEMY_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxShields())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_GROUND_SHIELDS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(306, FactKeys.ENEMY_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxShields())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_STATIC_AIR_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(307, FactKeys.ENEMY_STATIC_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_STATIC_GROUND_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(308, FactKeys.ENEMY_STATIC_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_STATIC_AIR_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(309, FactKeys.ENEMY_STATIC_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_STATIC_GROUND_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(310, FactKeys.ENEMY_STATIC_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_AIR_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(311, FactKeys.ENEMY_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_ENEMY_GROUND_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(312, FactKeys.ENEMY_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_AIR_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(313, FactKeys.OWN_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_GROUND_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(314, FactKeys.OWN_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_AIR_HP_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(315, FactKeys.OWN_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_GROUND_HP_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(316, FactKeys.OWN_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(v -> v.getCount() * v.getUnitTypeWrapper().getMaxHitPoints())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_STATIC_AIR_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(317, FactKeys.OWN_STATIC_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getAirWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_STATIC_GROUND_DMG_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(318, FactKeys.OWN_STATIC_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(
              v -> v.getCount() * v.getUnitTypeWrapper().getGroundWeapon().getDamageNormalized())
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_STATIC_AIR_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(319, FactKeys.OWN_STATIC_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_STATIC_GROUND_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(320, FactKeys.OWN_STATIC_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_AIR_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(321, FactKeys.OWN_AIR_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValueSet<UnitTypeStatus> SUM_OF_OWN_GROUND_UNITS_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(322, FactKeys.OWN_GROUND_FORCE_STATUS),
      optionalStream -> (double) optionalStream
          .orElse(Stream.empty())
          .mapToDouble(UnitTypeStatus::getCount)
          .sum()
  );
  public static final FactWithOptionalValue<Boolean> IS_BASE = new FactWithOptionalValue<>(
      new FactConverterID<>(323, FactKeys.IS_BASE), aBoolean -> aBoolean.orElse(false) ? 1.0 : 0.0);
  public static final FactWithOptionalValue<Boolean> IS_ENEMY_BASE = new FactWithOptionalValue<>(
      new FactConverterID<>(324, FactKeys.IS_ENEMY_BASE),
      aBoolean -> aBoolean.orElse(false) ? 1.0 : 0.0);
  public static final FactWithOptionalValue<Boolean> IS_MINERAL_ONLY = new FactWithOptionalValue<>(
      new FactConverterID<>(325, FactKeys.IS_MINERAL_ONLY),
      aBoolean -> aBoolean.orElse(false) ? 1.0 : 0.0);
  public static final FactWithOptionalValue<Boolean> IS_ISLAND = new FactWithOptionalValue<>(
      new FactConverterID<>(326, FactKeys.IS_ISLAND),
      aBoolean -> aBoolean.orElse(false) ? 1.0 : 0.0);
  public static final FactWithOptionalValue<Boolean> IS_START_LOCATION = new FactWithOptionalValue<>(
      new FactConverterID<>(327, FactKeys.IS_START_LOCATION),
      aBoolean -> aBoolean.orElse(false) ? 1.0 : 0.0);
  //defense
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_CREEP_COLONIES_AT_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(328, FactKeys.STATIC_DEFENSE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .map(AUnit::getType)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.CREEP_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_SPORE_COLONIES_AT_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(329, FactKeys.STATIC_DEFENSE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .map(AUnit::getType)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SPORE_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_SUNKEN_COLONIES_AT_BASE = new FactWithOptionalValueSet<>(
      new FactConverterID<>(330, FactKeys.STATIC_DEFENSE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .map(AUnit::getType)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SUNKEN_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_CREEP_COLONIES_AT_BASE_IN_CONSTRUCTION = new FactWithOptionalValueSet<>(
      new FactConverterID<>(331, FactKeys.WORKER_ON_BASE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .filter(AUnit::isMorphing)
          .filter(aUnitOfPlayer -> !aUnitOfPlayer.getTrainingQueue().isEmpty())
          .map(aUnitOfPlayer -> aUnitOfPlayer.getTrainingQueue().get(0))
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.CREEP_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_SPORE_COLONIES_AT_BASE_IN_CONSTRUCTION = new FactWithOptionalValueSet<>(
      new FactConverterID<>(332, FactKeys.STATIC_DEFENSE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .filter(aUnitOfPlayer -> aUnitOfPlayer.getType().equals(CREEP_COLONY_TYPE))
          .filter(aUnitOfPlayer -> !aUnitOfPlayer.getTrainingQueue().isEmpty())
          .map(aUnitOfPlayer -> aUnitOfPlayer.getTrainingQueue().get(0))
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SPORE_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> COUNT_OF_SUNKEN_COLONIES_AT_BASE_IN_CONSTRUCTION = new FactWithOptionalValueSet<>(
      new FactConverterID<>(333, FactKeys.STATIC_DEFENSE),
      vStream -> (double) vStream.orElse(Stream.empty())
          .filter(aUnitOfPlayer -> aUnitOfPlayer.getType().equals(CREEP_COLONY_TYPE))
          .filter(aUnitOfPlayer -> !aUnitOfPlayer.getTrainingQueue().isEmpty())
          .map(aUnitOfPlayer -> aUnitOfPlayer.getTrainingQueue().get(0))
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SUNKEN_COLONY_TYPE))
          .count());
  public static final FactWithOptionalValueSet<AUnitOfPlayer> BASE_IS_COMPLETED = new FactWithOptionalValueSet<>(
      new FactConverterID<>(334, FactKeys.HAS_BASE), vStream -> vStream.orElse(Stream.empty())
      .anyMatch(aUnitOfPlayer -> !aUnitOfPlayer.isBeingConstructed() && !aUnitOfPlayer.isMorphing())
      ? 1.0 : 0.0);


  //completed bases
  public static final FactWithOptionalValueSetsForAgentType<AUnitOfPlayer> COMPLETED_BASES = new FactWithOptionalValueSetsForAgentType<>(
      new FactConverterID<>(351, FactKeys.HAS_BASE), AgentTypes.BASE_LOCATION,
      optionalStream -> (double) optionalStream
          .filter(Optional::isPresent)
          .map(Optional::get)
          .filter(aUnitOfPlayerStream -> aUnitOfPlayerStream.anyMatch(
              aUnitOfPlayer -> !aUnitOfPlayer.isBeingConstructed() && !aUnitOfPlayer.isMorphing()))
          .count()
  );

  //building
  public static final FactWithOptionalValue<AUnitOfPlayer> IS_BEING_CONSTRUCT = new FactWithOptionalValue<>(
      new FactConverterID<>(401, FactKeys.REPRESENTS_UNIT),
      aUnit -> aUnit.get().isBeingConstructed() ? 1.0 : 0.0);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_POOLS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(402, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.SPAWNING_POOL);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitTypeWrapper> COUNT_OF_POOLS_IN_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(403, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SPAWNING_POOL_TYPE))
          .count(), AgentTypes.DRONE);
  public static final FactWithSetOfOptionalValuesForAgentType<Double> COUNT_OF_MINERALS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(404, FactKeys.AVAILABLE_MINERALS),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .mapToDouble(Optional::get)
          .sum(), AgentTypes.PLAYER);
  public static final FactWithSetOfOptionalValuesForAgentType<Double> COUNT_OF_GAS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(405, FactKeys.AVAILABLE_GAS),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .mapToDouble(Optional::get)
          .sum(), AgentTypes.PLAYER);
  public static final FactWithSetOfOptionalValues<AUnitTypeWrapper> COUNT_OF_HATCHERIES_IN_CONSTRUCTION = new FactWithSetOfOptionalValues<>(
      new FactConverterID<>(406, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.HATCHERY_TYPE))
          .count());
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_HATCHERIES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(407, FactKeys.REPRESENTS_UNIT),
      optionalStream -> 2.0 * (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(
              aUnitOfPlayer -> !aUnitOfPlayer.isBeingConstructed() && !aUnitOfPlayer.isMorphing())
          .count(), AgentTypes.HATCHERY);
  public static final FactWithSetOfOptionalValuesForAgentType<Boolean> COUNT_OF_HATCHERIES_BEING_CONSTRUCT = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(408, FactKeys.IS_BEING_CONSTRUCT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .filter(Optional::get)
          .count(), AgentTypes.HATCHERY);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_HETCH = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(409, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.HATCHERY);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_LAIRS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(410, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.LAIR);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_LAIRS_IN_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(411, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(aUnitOfPlayer -> !aUnitOfPlayer.getTrainingQueue().isEmpty())
          .filter(aUnitOfPlayer -> aUnitOfPlayer.getTrainingQueue().get(0)
              .equals(AUnitTypeWrapper.LAIR_TYPE))
          .count(), AgentTypes.HATCHERY);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_SPIRES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(412, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.SPIRE);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitTypeWrapper> COUNT_OF_SPIRES_IN_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(413, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.SPIRE_TYPE))
          .count(), AgentTypes.DRONE);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_HYDRALISK_DENS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(414, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.HYDRALISK_DEN);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitTypeWrapper> COUNT_OF_HYDRALISK_DENS_IN_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(415, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.HYDRALISK_DEN_TYPE))
          .count(), AgentTypes.DRONE);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_EVOLUTION_CHAMBERS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(416, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .count(), AgentTypes.EVOLUTION_CHAMBER);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitTypeWrapper> COUNT_OF_EVOLUTION_CHAMBERS_IN_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(417, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(AUnitTypeWrapper.EVOLUTION_CHAMBER_TYPE))
          .count(), AgentTypes.DRONE);
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_HATCHERIES_BEGINNING_CONSTRUCTION = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(418, FactKeys.REPRESENTS_UNIT),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(aUnitOfPlayer -> !aUnitOfPlayer.getTrainingQueue().isEmpty())
          .filter(aUnitOfPlayer -> aUnitOfPlayer.getTrainingQueue().get(0)
              .equals(AUnitTypeWrapper.HATCHERY_TYPE))
          .count(), AgentTypes.DRONE);

  //"barracks"
  public static final FactWithOptionalValue<AUnitOfPlayer> IS_TRAINING_QUEUE_EMPTY = new FactWithOptionalValue<>(
      new FactConverterID<>(501, FactKeys.REPRESENTS_UNIT),
      aUnit -> aUnit.get().getTrainingQueue().isEmpty() ? 1.0 : 0.0);
  public static final FactWithOptionalValue<AUnitOfPlayer> IS_MORPHING = new FactWithOptionalValue<>(
      new FactConverterID<>(502, FactKeys.REPRESENTS_UNIT),
      aUnit -> aUnit.get().isMorphing() ? 1.0 : 0.0);
  public static final FactWithSetOfOptionalValues<AUnitTypeWrapper> COUNT_OF_MORPHING_OVERLORDS = new FactWithSetOfOptionalValues<>(
      new FactConverterID<>(503, FactKeys.IS_MORPHING_TO),
      optionalStream -> (double) optionalStream.filter(Optional::isPresent)
          .map(Optional::get)
          .filter(typeWrapper -> typeWrapper.equals(OVERLORD_TYPE))
          .count());
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_SUPPLY_BY_OVERLORDS = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(504, FactKeys.REPRESENTS_UNIT), optionalStream -> optionalStream
      .filter(Optional::isPresent)
      .map(Optional::get)
      .filter(AUnit::isAlive)
      .mapToDouble(aUnitOfPlayer -> aUnitOfPlayer.getType().getSupplyProvided())
      .sum(), AgentTypes.OVERLORD);

  //scouting
  public static final FactWithSetOfOptionalValuesForAgentType<Integer> COUNT_OF_VISITED_BASES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(601, FactKeys.LAST_TIME_SCOUTED), optionalStream -> optionalStream
      .filter(Optional::isPresent)
      .count(), AgentTypes.BASE_LOCATION);
  public static final FactWithOptionalValue<Integer> LAST_TIME_SCOUTED = new FactWithOptionalValue<>(
      new FactConverterID<>(602, FactKeys.LAST_TIME_SCOUTED), integer -> integer.orElse(-1));

  //worker
  public static final FactWithOptionalValue<AUnit> IS_MINING_MINERAL = new FactWithOptionalValue<>(
      new FactConverterID<>(701, FactKeys.MINING_MINERAL), aUnit -> {
    if (aUnit.isPresent()) {
      return 1;
    }
    return 0;
  });
  public static final FactWithOptionalValue<AUnitWithCommands> IS_CARRYING_MINERAL = new FactWithOptionalValue<>(
      new FactConverterID<>(702, FactKeys.IS_UNIT), aUnit -> {
    if (aUnit.isPresent()) {
      if (aUnit.get().isCarryingMinerals()) {
        return 1;
      }
    }
    return 0;
  });
  public static final FactWithOptionalValue<AUnit> HAS_SELECTED_MINERAL_TO_MINE = new FactWithOptionalValue<>(
      new FactConverterID<>(703, FactKeys.MINERAL_TO_MINE), aUnit -> {
    if (aUnit.isPresent()) {
      return 1;
    }
    return 0;
  });
  public static final FactWithOptionalValue<Integer> MADE_BUILDING_LAST_CHECK = new FactWithOptionalValue<>(
      new FactConverterID<>(704, FactKeys.BUILDING_LAST_CHECK), integer -> integer.orElse(-1));
  public static final FactWithOptionalValue<Integer> LAST_OBSERVATION = new FactWithOptionalValue<>(
      new FactConverterID<>(705, FactKeys.MADE_OBSERVATION_IN_FRAME),
      integer -> integer.orElse(-1));
  public static final FactWithOptionalValue<AUnitOfPlayer> IS_WAITING_ON_MINERAL = new FactWithOptionalValue<>(
      new FactConverterID<>(706, FactKeys.REPRESENTS_UNIT), aUnit -> {
    if (aUnit.isPresent() && aUnit.get().getOrder().isPresent()
        && aUnit.get().getOrder().get().equals(Order.WaitForMinerals)) {
      return 1;
    }
    return 0;
  });
  public static final FactWithOptionalValue<AUnitWithCommands> IS_MINING_GAS = new FactWithOptionalValue<>(
      new FactConverterID<>(707, FactKeys.IS_UNIT),
      aUnit -> aUnit.get().isGatheringGas() ? 1.0 : 0.0
  );
  public static final FactWithOptionalValue<AUnitWithCommands> IS_CARRYING_GAS = new FactWithOptionalValue<>(
      new FactConverterID<>(708, FactKeys.IS_UNIT), aUnit -> aUnit.get().isCarryingGas() ? 1.0 : 0.0
  );
  public static final FactWithOptionalValue<AUnitWithCommands> IS_WAITING_ON_GAS = new FactWithOptionalValue<>(
      new FactConverterID<>(709, FactKeys.IS_UNIT), aUnit -> aUnit.get().getOrder().isPresent() && (
      aUnit.get().getOrder().get().equals(Order.MoveToGas) || aUnit.get().getOrder().get()
          .equals(Order.WaitForGas)
          || aUnit.get().getOrder().get().equals(Order.ReturnGas)) ? 1.0 : 0.0
  );
  public static final FactWithSetOfOptionalValuesForAgentType<AUnitOfPlayer> COUNT_OF_IDLE_DRONES = new FactWithSetOfOptionalValuesForAgentType<>(
      new FactConverterID<>(710, FactKeys.REPRESENTS_UNIT), optionalStream -> optionalStream
      .filter(Optional::isPresent)
      .map(Optional::get)
      .filter(AUnit::isIdle)
      .count(), AgentTypes.DRONE);
  public static final FactWithOptionalValue<AUnitWithCommands> IS_CONSTRUCTING_BUILDING = new FactWithOptionalValue<>(
      new FactConverterID<>(711, FactKeys.IS_UNIT), aUnit -> aUnit.get().isConstructing()
      || aUnit.get().isMorphing()
      || !aUnit.get().getTrainingQueue().isEmpty() ? 1.0 : 0.0
  );

  ////5 pool hack
  public static final FactWithSetOfOptionalValues<Boolean> CAN_TRANSIT_FROM_5_POOL = new FactWithSetOfOptionalValues<>(
      new FactConverterID<>(801, FactKeys.TRANSIT_FROM_5_POOL), optionalStream -> optionalStream
      .filter(Optional::isPresent)
      .anyMatch(Optional::get) ? 1.0 : 0.0);
}
