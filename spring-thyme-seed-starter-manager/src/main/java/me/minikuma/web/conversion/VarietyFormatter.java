package me.minikuma.web.conversion;

import lombok.RequiredArgsConstructor;
import me.minikuma.business.entity.Variety;
import me.minikuma.business.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class VarietyFormatter implements Formatter<Variety> {

    @Autowired
    private VarietyService varietyService;

    public VarietyFormatter() {
        super();
    }

    public Variety parse(final String text, final Locale locale) throws ParseException {
        final Integer varietyId = Integer.valueOf(text);
        return this.varietyService.findById(varietyId);
    }

    public String print(final Variety object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}
