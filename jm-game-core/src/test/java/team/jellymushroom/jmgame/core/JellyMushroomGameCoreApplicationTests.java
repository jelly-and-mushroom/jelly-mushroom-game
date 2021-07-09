package team.jellymushroom.jmgame.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import team.jellymushroom.jmgame.core.entity.game.PlayerEntity;

@SpringBootTest
@Slf4j
class JellyMushroomGameCoreApplicationTests {

	@Test
	void test() {
		log.info(new PlayerEntity().getCurrentMaxAction().toString());
	}

}
