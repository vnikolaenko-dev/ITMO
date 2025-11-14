package vnikolaenko.github.download.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DownloadFiledEvent {
    private String username;
}
