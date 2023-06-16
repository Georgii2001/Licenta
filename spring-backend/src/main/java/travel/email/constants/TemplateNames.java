package travel.email.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateNames {
    USER_HAS_NEW_MATCHES("userHasNewMatches.ftl"),
    INVITE_TO_NEW_TRIP("inviteToNewTrip.ftl");

    public final String ftlName;

}
