package com.codecool.processwatch.domain;

import java.util.stream.Stream;

/**
 * Represents a source that can provide a snapshot of the current processes.
 * and do operations on them (eg. kill)
 */
public interface ProcessSource {
    /**
     * Get a snapshot of the current processes.
     *
     * @return a {@code Stream} of processes.
     */
    Stream<Process> getProcesses();


    /**
     * Kills the process with the given info if it is found
     */
    void killProcess(long processId);
}
