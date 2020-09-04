package com.codecool.processwatch.queries;

import com.codecool.processwatch.domain.Process;
import com.codecool.processwatch.domain.Query;
import com.codecool.processwatch.domain.User;
import com.codecool.processwatch.os.OsProcessSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class QueryByInput implements Query {

    String textToFilter = "";

    public QueryByInput(String textToFilter) {
        this.textToFilter = textToFilter;
    }


    @Override
    public Stream<Process> run(Stream<Process> input) {
        return input.filter(process -> {
                try {
                    if (Integer.parseInt(textToFilter) == process.getParentPid()) {
                        return true;
                    }}
                catch (NumberFormatException e) {
                    if (textToFilter.equals(process.getUserName())) {
                        return true;
                    }
                }
                return false;
        });
    }
}