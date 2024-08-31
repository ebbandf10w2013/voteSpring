CREATE TABLE polls (
    id BIGINT PRIMARY KEY IDENTITY(1,1),  -- 自增的主键
    title NVARCHAR(255) NOT NULL,          -- 非空的标题字段
    vote_count BIGINT NOT NULL DEFAULT 0   -- 累积票数，默认为0
);
CREATE TABLE votes (
    id BIGINT PRIMARY KEY IDENTITY(1,1),  -- 自增的主键
    poll_id BIGINT NOT NULL,              -- 关联到 polls 表的外键
    voter NVARCHAR(255) NOT NULL,         -- 投票人名称，非空

    CONSTRAINT FK_Poll_Votes FOREIGN KEY (poll_id) REFERENCES polls(id) ON DELETE CASCADE
);
--新增投票選項
INSERT INTO polls (title, [voteCount]) VALUES ('Best Programming Language', 0);
INSERT INTO polls (title, [voteCount]) VALUES ('Favorite Database', 0);
--新增投票明細
INSERT INTO votes (poll_id, voter) VALUES (1, 'Alice');
INSERT INTO votes (poll_id, voter) VALUES (1, 'Bob');
INSERT INTO votes (poll_id, voter) VALUES (2, 'Charlie');
INSERT INTO votes (poll_id, voter) VALUES (2, 'Dave');



--==================================


CREATE PROCEDURE InsertPoll
    @Title NVARCHAR(255),
    @VoteCount BIGINT = 0
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO polls (title, [voteCount])
    VALUES (@Title, @VoteCount);

    SELECT SCOPE_IDENTITY() AS NewPollId; -- 返回新插入的 Poll ID
END;
--==================================
CREATE PROCEDURE InsertVote
    @PollId BIGINT,
    @Voter NVARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO votes (poll_id, voter)
    VALUES (@PollId, @Voter);

    -- 更新对应 Poll 的 vote_count
    UPDATE polls
    SET vote_count = vote_count + 1
    WHERE id = @PollId;

    SELECT SCOPE_IDENTITY() AS NewVoteId; -- 返回新插入的 Vote ID
END;
