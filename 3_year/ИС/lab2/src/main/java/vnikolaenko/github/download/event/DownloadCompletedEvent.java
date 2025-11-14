package vnikolaenko.github.download.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DownloadCompletedEvent {
    private String username;
    private int count;
}
