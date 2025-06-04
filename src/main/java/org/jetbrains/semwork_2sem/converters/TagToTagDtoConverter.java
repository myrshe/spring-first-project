package org.jetbrains.semwork_2sem.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.jetbrains.semwork_2sem.dto.TagDto;
import org.jetbrains.semwork_2sem.models.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagToTagDtoConverter implements Converter<Tag, TagDto> {

    @Override
    public TagDto convert(Tag source) {
        if (source == null) {
            return null;
        }
        TagDto dto = new TagDto();
        dto.setName(source.getName());
        return dto;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
