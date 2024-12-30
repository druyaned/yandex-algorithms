package druyaned.yandexalgorithms.train5.l3setmap.p09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayFootball {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        Parser parser = new Parser(reader);
        parser.writeOutput(writer);
    }
    
}

class Team {
    
    private final String name;
    private int goalCount;
    private int matchCount;
    private int scoreOpenCount;
    
    public Team(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public int getGoalCount() {
        return goalCount;
    }
    
    public int getMatchCount() {
        return matchCount;
    }
    
    public int getScoreOpenCount() {
        return scoreOpenCount;
    }
    
    public void addGoal() {
        goalCount++;
    }
    
    public void addMatch() {
        matchCount++;
    }
    
    public void addScoreOpen() {
        scoreOpenCount++;
    }
    
}

class Player {
    
    private final String name;
    private final Team team;
    private int goalCount;
    private int scoreOpenCount;
    private final int[] minuteToGoalCount = new int[Parser.MINUTES_IN_MATCH + 1];
    
    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }
    
    public String getName() {
        return name;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public int getGoalCount() {
        return goalCount;
    }
    
    public int getScoreOpenCount() {
        return scoreOpenCount;
    }
    
    public int getGoalCountIn(int minute) {
        return minuteToGoalCount[minute];
    }
    
    public void addGoal(int minute) {
        team.addGoal();
        goalCount++;
        minuteToGoalCount[minute]++;
    }
    
    public void addScoreOpen() {
        team.addScoreOpen();
        scoreOpenCount++;
    }
    
}

interface Request {
    
    String getId();
    
    void process(StringBuilder output, Object... args);
    
}

class TeamTotalGoals implements Request {
    
    @Override public String getId() {
        return "Total goals for";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Team team = (Team)args[0];
        if (team != null) {
            output.append(team.getGoalCount()).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class TeamMeanGoalsPerGame implements Request {
    
    @Override public String getId() {
        return "Mean goals per game for";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Team team = (Team)args[0];
        double meanGoalsPerGame = (double)team.getGoalCount() / team.getMatchCount();
        output.append(meanGoalsPerGame).append('\n');
    }
    
}

class PlayerTotalGoals implements Request {
    
    @Override public String getId() {
        return "Total goals by";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Player player = (Player)args[0];
        if (player != null) {
            output.append(player.getGoalCount()).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class PlayerMeanGoalsPerGame implements Request {
    
    @Override public String getId() {
        return "Mean goals per game by";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Player player = (Player)args[0];
        double goalCount = (double)player.getGoalCount();
        double matchCount = (double)player.getTeam().getMatchCount();
        output.append(goalCount / matchCount).append('\n');
    }
    
}

class OnMinuteGoalsByPlayer implements Request {
    
    @Override public String getId() {
        return "Goals on minute";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        int minute = (Integer)args[0];
        Player player = (Player)args[1];
        if (player != null) {
            output.append(player.getGoalCountIn(minute)).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class ToMinuteGoalsByPlayer implements Request {
    
    @Override public String getId() {
        return "Goals on first";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        int minute = (Integer)args[0];
        Player player = (Player)args[1];
        if (player != null) {
            int goalCountToMinute = 0;
            for (int m = 1; m <= minute; m++) {
                goalCountToMinute += player.getGoalCountIn(m);
            }
            output.append(goalCountToMinute).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class FromGoalsByPlayer implements Request {
    
    @Override public String getId() {
        return "Goals on last";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        int minute = (Integer)args[0];
        Player player = (Player)args[1];
        if (player != null) {
            int goalCountToMinute = 0;
            for (int m = 91 - minute; m <= 90; m++) {
                goalCountToMinute += player.getGoalCountIn(m);
            }
            output.append(goalCountToMinute).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class TeamScoreOpens implements Request {
    
    @Override public String getId() {
        return "Score opens by Team";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Team team = (Team)args[0];
        if (team != null) {
            output.append(team.getScoreOpenCount()).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class PlayerScoreOpens implements Request {
    
    @Override public String getId() {
        return "Score opens by Player";
    }
    
    @Override public void process(StringBuilder output, Object... args) {
        Player player = (Player)args[0];
        if (player != null) {
            output.append(player.getScoreOpenCount()).append('\n');
        } else {
            output.append("0\n");
        }
    }
    
}

class Parser {
    
    public static final int MINUTES_IN_MATCH = 90;
    public static final int MAX_MATCH_COUNT = 20;
    public static final int MAX_PLAYER_COUNT = 200;
    private static final int LINE_COUNT_LIMIT = 10000;
    private static final int OUTPUT_SIZE_LIMIT = 1000000;
    private static final int RADIX = 10;
    
    private final List<String> lines = new ArrayList(LINE_COUNT_LIMIT);
    private final Map<String, Team> teams = new HashMap(MAX_MATCH_COUNT);
    private final Map<String, Player> players = new HashMap(MAX_PLAYER_COUNT);
    private final Map<String, Request> requests = new HashMap();
    private final StringBuilder output = new StringBuilder(OUTPUT_SIZE_LIMIT);
    
    public Parser(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            lines.add(reader.readLine());
        }
        buildRequests();
        for (int i = 0; i < lines.size(); ) {
            char first = lines.get(i).charAt(0);
            if (first == '"') {
                i = parseMatch(i);
            } else {
                parseRequest(lines.get(i++));
            }
        }
    }
    
    private void putRequest(Request request) {
        requests.put(request.getId(), request);
    }
    
    private void buildRequests() {
        putRequest(new TeamTotalGoals());
        putRequest(new TeamMeanGoalsPerGame());
        putRequest(new PlayerTotalGoals());
        putRequest(new PlayerMeanGoalsPerGame());
        putRequest(new OnMinuteGoalsByPlayer());
        putRequest(new ToMinuteGoalsByPlayer());
        putRequest(new FromGoalsByPlayer());
        putRequest(new TeamScoreOpens());
        putRequest(new PlayerScoreOpens());
    }
    
    private int parseMatch(int lineIndex) {
        // parse teams part
        String header = lines.get(lineIndex++);
        int beginIndex = 1, endIndex = 1;
        while (header.charAt(endIndex) != '"') {
            endIndex++;
        }
        String team1Name = header.substring(beginIndex, endIndex);
        beginIndex = endIndex += 5; // skip("\" - \"")
        while (header.charAt(endIndex) != '"') {
            endIndex++;
        }
        String team2Name = header.substring(beginIndex, endIndex);
        beginIndex = endIndex += 2; // ship("\" ")
        while (header.charAt(endIndex) != ':') {
            endIndex++;
        }
        int score1 = Integer.parseInt(header, beginIndex, endIndex, RADIX);
        beginIndex = endIndex + 1; // ship(":")
        endIndex = header.length();
        int score2 = Integer.parseInt(header, beginIndex, endIndex, RADIX);
        Team team1 = teams.get(team1Name);
        if (team1 == null) {
            team1 = new Team(team1Name);
            teams.put(team1Name, team1);
        }
        Team team2 = teams.get(team2Name);
        if (team2 == null) {
            team2 = new Team(team2Name);
            teams.put(team2Name, team2);
        }
        team1.addMatch();
        team2.addMatch();
        // parse players part
        int minMinute = 91;
        String trackedName = null;
        for (int i = 0; i < score1 + score2; i++) {
            String line = lines.get(lineIndex++);
            beginIndex = endIndex = 0;
            while ('0' > line.charAt(endIndex) || line.charAt(endIndex) > '9') {
                endIndex++;
            }
            String playerName = line.substring(beginIndex, endIndex - 1);
            beginIndex = endIndex;
            while (line.charAt(endIndex) != '\'') {
                endIndex++;
            }
            int minute = Integer.parseInt(line, beginIndex, endIndex, RADIX);
            Player player = players.get(playerName);
            if (player == null) {
                player = i < score1 ?
                        new Player(playerName, team1) :
                        new Player(playerName, team2);
                players.put(playerName, player);
            }
            player.addGoal(minute);
            if (minMinute > minute) {
                minMinute = minute;
                trackedName = playerName;
            }
        }
        if (trackedName != null) {
            Player player = players.get(trackedName);
            player.addScoreOpen();
        }
        return lineIndex;
    }
    
    private void parseRequest(String line) {
        int beginIndex = 0, endIndex = 0;
        while (line.charAt(endIndex) != ' ') {
            endIndex++;
        }
        String firstWord = line.substring(beginIndex, endIndex);
        endIndex++;
        while (line.charAt(endIndex) != ' ') {
            endIndex++;
        }
        endIndex++;
        while (line.charAt(endIndex) != ' ') {
            endIndex++;
        }
        if (firstWord.equals("Goals")) {
            String firstThreeWords = line.substring(beginIndex, endIndex);
            beginIndex = endIndex += 1;
            while (line.charAt(endIndex) != ' ') {
                endIndex++;
            }
            Integer minute = Integer.valueOf(line.substring(beginIndex, endIndex), RADIX);
            beginIndex = endIndex += 1;
            while (line.charAt(endIndex) != ' ') {
                endIndex++;
            }
            while (!line.substring(beginIndex, endIndex).equals("by")) {
                beginIndex = endIndex += 1;
                while (line.charAt(endIndex) != ' ') {
                    endIndex++;
                }
            }
            beginIndex = endIndex + 1;
            endIndex = line.length();
            Player player = players.get(line.substring(beginIndex, endIndex));
            Request request = requests.get(firstThreeWords);
            request.process(output, minute, player);
        }
        if (firstWord.equals("Mean")) {
            endIndex++;
            while (line.charAt(endIndex) != ' ') {
                endIndex++;
            }
            endIndex++;
            while (line.charAt(endIndex) != ' ') {
                endIndex++;
            }
            String firstFiveWords = line.substring(beginIndex, endIndex);
            if (line.charAt(++endIndex) == '"') {
                beginIndex = endIndex + 1;
                endIndex = line.length() - 1;
                String teamName = line.substring(beginIndex, endIndex);
                Team team = teams.get(teamName);
                Request request = requests.get(firstFiveWords);
                request.process(output, team);
            } else {
                beginIndex = endIndex;
                endIndex = line.length();
                String playerName = line.substring(beginIndex, endIndex);
                Player player = players.get(playerName);
                Request request = requests.get(firstFiveWords);
                request.process(output, player);
            }
        }
        if (firstWord.equals("Score")) {
            String firstThreeWords = line.substring(beginIndex, endIndex);
            if (line.charAt(++endIndex) == '"') {
                beginIndex = endIndex + 1;
                endIndex = line.length() - 1;
                String teamName = line.substring(beginIndex, endIndex);
                Team team = teams.get(teamName);
                Request request = requests.get(firstThreeWords + " Team");
                request.process(output, team);
            } else {
                beginIndex = endIndex;
                endIndex = line.length();
                String playerName = line.substring(beginIndex, endIndex);
                Player player = players.get(playerName);
                Request request = requests.get(firstThreeWords + " Player");
                request.process(output, player);
            }
        }
        if (firstWord.equals("Total")) {
            String firstThreeWords = line.substring(beginIndex, endIndex);
            if (line.charAt(++endIndex) == '"') {
                beginIndex = endIndex + 1;
                endIndex = line.length() - 1;
                String teamName = line.substring(beginIndex, endIndex);
                Team team = teams.get(teamName);
                Request request = requests.get(firstThreeWords);
                request.process(output, team);
            } else {
                beginIndex = endIndex;
                endIndex = line.length();
                String playerName = line.substring(beginIndex, endIndex);
                Player player = players.get(playerName);
                Request request = requests.get(firstThreeWords);
                request.process(output, player);
            }
        }
    }
    
    public void writeOutput(BufferedWriter writer) throws IOException {
        writer.write(output.toString());
    }
    
}
/*
Example {
input:
    "Juventus" - "Milan" 3:1
    Inzaghi 45'
    Del Piero 67'
    Del Piero 90'
    Shevchenko 34'
    Total goals for "Juventus"
    Total goals by Pagliuca
    Mean goals per game by Inzaghi
    "Juventus" - "Lazio" 0:0
    Mean goals per game by Inzaghi
    Mean goals per game by Shevchenko
    Score opens by Inzaghi
output:
    3
    0
    1.0
    0.5
    1.0
    0
}

Input data format {
    "<team1>" - "<team2>" <score1>:<score2>
    <player1> <minute1>' // loop ( i in [1, score1] );
    ...
    <player2> <minute2>' // loop ( i in [1, score2] );
    ...
    <request>...
}

Requests {
    Total goals for "<team>" — количество голов, забитое данной командой за все матчи.
    Mean goals per game for "<team>" — среднее количество голов,
        забиваемое данной командой за один матч.
        Гарантирутся, что к моменту подачи такого запроса команда уже сыграла хотя бы один матч.
    Total goals by <player> — количество голов, забитое данным игроком за все матчи.
    Mean goals per game by <player> — среднее количество голов,
        забиваемое данным игроком за один матч его команды.
        Гарантирутся, что к моменту подачи такого запроса игрок уже забил хотя бы один гол.
    Goals on minute <Минута> by <player> — количество голов,
        забитых данным игроком ровно на указанной минуте матча.
    Goals on first <T> minutes by <player> — количество голов,
        забитых данным игроком на минутах с первой по T-ю включительно.
    Goals on last <T> minutes by <player> — количество голов,
        забитых данным игроком на минутах с (91 - T)-й по 90-ю включительно.
    Score opens by "<team>" — сколько раз данная команда открывала счет в матче.
    Score opens by <player> — сколько раз данный игрок открывал счет в матче.
}

Architecture {
    Team {
        String name;
        int goalCount;
        int matchCount;
        int scoreOpenCount;
    }
    Player {
        String name;
        Team team;
        int goalCount;
        int scoreOpenCount;
        int[] minuteToGoalCount;
    }
    Request : interface {
        // input if starts not with '"'
        String getId();
        void process(StringBuilder output, Object... args);
    }
    Requests {
        TeamTotalGoals;
        TeamMeanGoalsPerGame;
        PlayerTotalGoals;
        PlayerMeanGoalsPerGame;
        OnMinuteGoalsByPlayer;
        ToMinuteGoalsByPlayer;
        FromGoalsByPlayer;
        TeamScoreOpens;
        PlayerScoreOpens;
    }
    Parser {
        Map<String, Team> teams;
        Map<String, Player> players;
        Map<String, Request> requests;
        StringBuilder output;
        
        void buildRequests();
        void parse(String[] lines); // general method to parse full input
        void parseTeamsAndPlayers(String line);
        void parseRequest(String line); // outputBuilder is updated
        void writeOutput(BufferedWriter writer);
    }
}

Request IDs {
    ToMinuteGoalsByPlayer   :   Goals on first
    FromGoalsByPlayer       :   Goals on last
    OnMinuteGoalsByPlayer   :   Goals on minute
    PlayerMeanGoalsPerGame  :   Mean goals per game by
    TeamMeanGoalsPerGame    :   Mean goals per game for
    PlayerScoreOpens        :   Score opens by Player
    TeamScoreOpens          :   Score opens by Team
    PlayerTotalGoals        :   Total goals by
    TeamTotalGoals          :   Total goals for
}

Input description {
    Входной файл содержит информацию о матчах и запросы в том порядке,
        в котором они поступают в программу Аси Вуткиной.
    Во входном файле содержится информация не более чем о 100 матчах,
        в каждом из которых забито не более 10 голов. Всего в чемпионате
        участвует не более 20 команд, в каждой команде не более 10 игроков забивают голы.
    Все названия команд и имена игроков состоят
        только из прописных и строчных латинских букв и пробелов,
        а их длина не превышает 30. Прописные и строчные буквы считаются различными.
        Имена и названия не начинаются и не оканчиваются пробелами
        и не содержат двух пробелов подряд. Каждое имя и название содержит хотя бы одну букву.
    Минута, на которой забит гол - целое число от 1 до 90
        (про голы, забитые в дополнительное время, принято говорить,
        что они забиты на 90-й минуте).
    Для простоты будем считать, что голов в собственные ворота
        в европейских чемпионатах не забивают, и на одной минуте матча
        может быть забито не более одного гола (в том числе на 90-й).
        Во время чемпионата игроки не переходят из одного клуба в другой.
    Количество запросов во входном файле не превышает 500.
}
*/
