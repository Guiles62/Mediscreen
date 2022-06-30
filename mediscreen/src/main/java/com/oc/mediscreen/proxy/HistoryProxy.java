package com.oc.mediscreen.proxy;

import com.oc.mediscreen.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient( name = "history", url = "${history.url}")
public interface HistoryProxy {

    @PostMapping(value = "patHistory/add")
    Note addNote(@RequestParam int patId, @RequestParam String e);

    @GetMapping(value = "patHistory/{patId}")
    List<Note> getPatientNote(@PathVariable("patId") int patId);

}
