package com.fangyu3.webquizengine.mappers;

import com.fangyu3.webquizengine.domain.UserCompletion;
import com.fangyu3.webquizengine.web.model.UserCompletionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserCompletionMapper {

    @Mapping(target="id", source="quizId")
    public UserCompletionDto userCompletionToUserCompletionDto (UserCompletion userCompletion);

    public UserCompletion userCompletionDtoToUserCompletion (UserCompletionDto userCompletionDto);
}
