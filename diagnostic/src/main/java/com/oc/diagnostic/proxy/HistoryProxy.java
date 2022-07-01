package com.oc.diagnostic.proxy;

import com.oc.diagnostic.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient( name = "history", url = "${history_url}")
public interface HistoryProxy {

    @GetMapping(value = "patHistory/{patId}")
    List<Note> getPatientNote(@PathVariable("patId") int patId);
}
