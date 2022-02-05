package ir.maktab.data.dto.mappers;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.entity.Comment;

public class CommentMapper {

    public static Comment toComment(CommentDto commentDto){
        return Comment.builder().setExplanations(commentDto.getComment()).setPoint(commentDto.getPoint()).build();
    }
}
