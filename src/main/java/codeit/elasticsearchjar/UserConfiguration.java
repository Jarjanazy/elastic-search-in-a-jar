package codeit.elasticsearchjar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserConfiguration {
    private int port;
    private String tag;
}
