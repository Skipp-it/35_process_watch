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

        ArrayList filteredStream = new ArrayList();

//        input.forEach(process -> {
//            try {
//                if (Integer.parseInt(textToFilter) == process.getParentPid()) {
//                    filteredStream.add(new Process(
//                            process.getPid(),
//                            process.getParentPid(),
//                            new User(process.getUserName()),
//                            process.getName(),
//                            process.getArgs()));
//                }}
//            catch (NumberFormatException e) {
//                if (textToFilter.equals(process.getUserName())) {
//                    filteredStream.add(new Process(
//                            process.getPid(),
//                            process.getParentPid(),
//                            new User(process.getUserName()),
//                            process.getName(),
//                            process.getArgs()));
//                }
//            }
//        });
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
//        return filteredStream.stream();
    }
}

/**
 * This is the identity query.  It selects everything from its source.
 * public class SelectAll implements Query {
 * <p>
 * {@inheritDoc}
 *
 * @Override public Stream<Process> run(Stream<Process> input) {
 * return input;
 * }
 **/


//@Override
//public Stream<Process> run(Stream<Process> input) {
//    OsProcessSource allProcesesObj = new OsProcessSource();
//
//    ArrayList filteredStream = new ArrayList();
//    input= allProcesesObj.getProcesses();
//
//    input.forEach(process -> {
//        if (textToFilter.equals(process.getName())) {
//            filteredStream.add(new Process(
//                    process.getPid(),
//                    process.getParentPid(),
//                    new User(process.getName()),
//                    process.getUserName(),
//                    process.getArgs()));
//        }
//    });
//    return filteredStream.stream();
//}
//}