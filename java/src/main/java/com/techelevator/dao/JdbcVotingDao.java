package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.PostDto;
import com.techelevator.model.ResponseDto;
import com.techelevator.model.VotingDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class JdbcVotingDao implements VotingDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcVotingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResponseDto addEntryAndIncrementUpvote(ResponseDto upvotedResponse, Principal principal) {

        //RAW ADD NO VALIDATION
        String sqlAddToJoin = "INSERT INTO response_user_vote (toggle_status, response_id, user_id) " +
                "VALUES (true, ?, (SELECT user_id FROM users WHERE username = ?)) RETURNING response_user_vote_id;";
        String sqlIncrementResponseUpvote = "UPDATE responses " +
                "SET upvotes = upvotes+1 " +
                "WHERE response_id = ?;";
        try{
            //Insert new entry into the join with a value of TRUE
           int newJoinId = jdbcTemplate.queryForObject(sqlAddToJoin, int.class, upvotedResponse.getResponseId(), principal.getName());
           //Increment upvotes by 1
           jdbcTemplate.update(sqlIncrementResponseUpvote, upvotedResponse.getResponseId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return upvotedResponse;
    }

    @Override
    public ResponseDto deleteEntryAndDecrementUpvote(ResponseDto responseDto, Principal principal) {
        String sqlDeleteFromJoin= "DELETE FROM response_user_vote \n" +
                "WHERE response_id = ? AND user_id = (SELECT user_id FROM users WHERE username = ?);";
        String sqlDecrementResponseUpvote = "UPDATE responses " +
                "SET upvotes = upvotes-1 " +
                "WHERE response_id = ?;";
        try{
            //Insert new entry into the join with a value of TRUE
             jdbcTemplate.update(sqlDeleteFromJoin, responseDto.getResponseId(), principal.getName());
            //decrement upvotes by 1
            jdbcTemplate.update(sqlDecrementResponseUpvote, responseDto.getResponseId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return responseDto;
    }

    //Gets voting DTO for validaiton in service class
    public VotingDto getVotingDtoForValidation (ResponseDto responseDto, Principal principal){
            VotingDto votingDto = new VotingDto();

        String sql = "SELECT response_user_vote_id, toggle_status, response_id, user_id " +
                "FROM response_user_vote " +
                "WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND response_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, principal.getName(), responseDto.getResponseId());
            if(results.next()){
                votingDto = mapResultsToVotingDto(results);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return votingDto;
    }

    
    private VotingDto mapResultsToVotingDto (SqlRowSet rowSet){

        VotingDto votingDto = new VotingDto();
        votingDto.setVoteStatus(rowSet.getBoolean("toggle_status"));
        votingDto.setObjectId(rowSet.getInt("response_id"));
        votingDto.setResponseUserId(rowSet.getInt("response_user_vote_id"));
        votingDto.setUserId(rowSet.getInt("user_id"));

        return votingDto;

    }


}
