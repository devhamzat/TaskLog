package org.hae.tasklogue.dto.requestdto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EditProfileRequest {
    private String displayName;
    private String bio;
    private String photoUrl;

}
