package team.jellymushroom.fullmoon.entity.game.card;

import lombok.Data;

/**
 * 卡牌
 * 每张卡牌都是无状态的，单例的
 * 每个应用在初始化时会构造全部卡牌，且不会再修改
 */
@Data
public abstract class CardEntity {
}
