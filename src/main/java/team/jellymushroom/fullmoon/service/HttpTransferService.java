package team.jellymushroom.fullmoon.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpTransferService {

  @Value("${fm.http.opponent.host}")
  @Getter
  private String httpOpponentHost;

  public static final Long HTTP_RETRY_INTERVAL = 1000L;
}
