package team.jellymushroom.jmgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * 除了springboot项目初始化得到的启动代码外，不添加额外的逻辑
 */
@SpringBootApplication
public class JellyMushroomGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(JellyMushroomGameApplication.class, args);
	}

}
