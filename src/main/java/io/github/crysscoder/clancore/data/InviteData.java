package io.github.crysscoder.clancore.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class InviteData {
    private final UUID inviter;
    private final String clanName;
    private final long expireTime;
}

