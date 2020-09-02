package com.codecool.processwatch.os;

import java.util.*;
import java.util.stream.*;
import java.lang.ProcessHandle;


import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.ProcessSource;
import com.codecool.processwatch.domain.User;


/**
 * A process source using the Java {@code ProcessHandle} API to retrieve information
 * about the current processes.
 */
public class OsProcessSource implements ProcessSource {
    /**
     * {@inheritDoc}
     *
     * @return
     */


    @Override
    public Stream<Process> getProcesses() {

        ArrayList<Process> processes = new ArrayList<>();

        ProcessHandle.allProcesses().forEach(p -> {
            processes.add(new Process(
                    p.pid(),
                    Integer.parseInt(p.parent().map(ProcessHandle::pid).map(Objects::toString).orElse("0")),
                    new User(p.info().user().get()),
                    p.info().command()
                            .map(Object::toString)
                            .map(s -> s.split("/"))
                            .map(a -> a[a.length-1])
                            .orElse("not available"),
                    new String[]{Arrays.toString(p.info().arguments().orElse(new String[]{}))}
            ));
        });

        return processes.stream();
    }
}
