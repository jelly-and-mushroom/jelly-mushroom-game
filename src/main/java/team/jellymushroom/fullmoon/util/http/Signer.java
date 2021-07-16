package team.jellymushroom.fullmoon.util.http;

import com.google.common.collect.ImmutableMap;

public interface Signer {

  ImmutableMap<String, String> sign(ImmutableMap<String, String> params);
}
