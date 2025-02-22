package nextstep.sessions.infrastructore;

import nextstep.sessions.domain.FreeSession;
import nextstep.sessions.domain.PaidSession;
import nextstep.sessions.domain.RecruitmentState;
import nextstep.sessions.domain.Selection;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@JdbcTest
class JdbcSessionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository repository;

    @BeforeEach
    void setUp() {
        CoverImageRepository coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
        repository = new JdbcSessionRepository(jdbcTemplate, coverImageRepository);
    }

    @Test
    void save() {
        Session session = Session.builder()
                .courseId(1L)
                .title("TDD, 자바 99기")
                .sessionType(new PaidSession(999, 800000L))
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build();

        assertThat(repository.save(session)).isEqualTo(4L);
    }


    @Test
    void saveAll() {
        Session session1 = Session.builder()
                .courseId(1L)
                .title("TDD, 자바 99기")
                .sessionType(new PaidSession(999, 800000L))
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build();

        Session session2 = Session.builder()
                .courseId(2L)
                .title("스터디 모집")
                .sessionType(new FreeSession())
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .createdAt(LocalDateTime.now())
                .build();

        assertThatNoException()
                .isThrownBy(() -> repository.saveAll(List.of(session1, session2)));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void findById(long sessionId) {
        Optional<Session> session = repository.findById(sessionId);
        assertThat(session.isEmpty()).isFalse();
        assertThat(session.isPresent()).isTrue();

        LOGGER.debug("Session : {}", session.get());
    }

    @Test
    void findByIds() {
        List<Session> sessions = repository.findByIds(List.of(1L, 2L, 3L));

        assertThat(sessions).hasSize(3);

        LOGGER.debug("Sessions : {}", sessions);
    }

    @Test
    void findByCourseId() {
        List<Session> sessions = repository.findByCourseId(1L);
        assertThat(sessions).hasSize(1);

        LOGGER.debug("Sessions : {}", sessions);
    }

    @Test
    void update() {
        Session session = repository.findById(1L).get();
        Session target = Session.builder()
                .id(1L)
                .courseId(1L)
                .title("TDD, 클린 코드 with Java 18기 (수정)")
                .state(SessionState.ONGOING) // before : 'FINISHED'
                .recruitment(RecruitmentState.RECRUITING) // before: 'NOT_RECRUITING'
                .sessionType(new FreeSession())
                .startDate(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2024, 12, 31, 23, 59, 59))
                .updatedAt(LocalDateTime.now())
                .selection(Selection.MANUAL)
                .build();

        session.update(target);

        int count = repository.update(session);
        assertThat(count).isEqualTo(1);

        LOGGER.info("Updated Session : {}", session);
    }
}
