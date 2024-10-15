package com.example.TagFinder.ModalOfApi;

import java.util.List;

public class MatchResponse {
    private List<TypeMatch> typeMatches;

    public List<TypeMatch> getTypeMatches() {
        return typeMatches;
    }

    public void setTypeMatches(List<TypeMatch> typeMatches) {
        this.typeMatches = typeMatches;
    }

    // Nested class TypeMatch
    public static class TypeMatch {
        private String matchType;
        private List<SeriesMatches> seriesMatches;

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public List<SeriesMatches> getSeriesMatches() {
            return seriesMatches;
        }

        public void setSeriesMatches(List<SeriesMatches> seriesMatches) {
            this.seriesMatches = seriesMatches;
        }

        // Nested class SeriesMatches
        public static class SeriesMatches {
            private SeriesAdWrapper seriesAdWrapper;

            public SeriesAdWrapper getSeriesAdWrapper() {
                return seriesAdWrapper;
            }

            public void setSeriesAdWrapper(SeriesAdWrapper seriesAdWrapper) {
                this.seriesAdWrapper = seriesAdWrapper;
            }

            // Nested class SeriesAdWrapper
            public static class SeriesAdWrapper {
                private String seriesId;
                private String seriesName;
                private List<Matches> matches;

                public String getSeriesId() {
                    return seriesId;
                }

                public void setSeriesId(String seriesId) {
                    this.seriesId = seriesId;
                }

                public String getSeriesName() {
                    return seriesName;
                }

                public void setSeriesName(String seriesName) {
                    this.seriesName = seriesName;
                }

                public List<Matches> getMatches() {
                    return matches;
                }

                public void setMatches(List<Matches> matches) {
                    this.matches = matches;
                }

                // Nested class Matches
                public static class Matches {
                    private MatchInfo matchInfo;
                    private MatchScore matchScore;

                    public MatchInfo getMatchInfo() {
                        return matchInfo;
                    }

                    public void setMatchInfo(MatchInfo matchInfo) {
                        this.matchInfo = matchInfo;
                    }

                    public MatchScore getMatchScore() {
                        return matchScore;
                    }

                    public void setMatchScore(MatchScore matchScore) {
                        this.matchScore = matchScore;
                    }

                    // Nested class MatchInfo
                    public static class MatchInfo {
                        private int matchId;
                        private int seriesId;
                        private String seriesName;
                        private String matchDesc;
                        private String matchFormat;
                        private String startDate;
                        private String endDate;
                        private String state;
                        private String status;
                        private Team team1;
                        private Team team2;
                        private VenueInfo venueInfo;
                        private int currBatTeamId;
                        private String seriesStartDt;
                        private String seriesEndDt;
                        private boolean isTimeAnnounced;
                        private String stateTitle;
                        private boolean isFantasyEnabled;

                        public int getMatchId() {
                            return matchId;
                        }

                        public void setMatchId(int matchId) {
                            this.matchId = matchId;
                        }

                        public int getSeriesId() {
                            return seriesId;
                        }

                        public void setSeriesId(int seriesId) {
                            this.seriesId = seriesId;
                        }

                        public String getSeriesName() {
                            return seriesName;
                        }

                        public void setSeriesName(String seriesName) {
                            this.seriesName = seriesName;
                        }

                        public String getMatchDesc() {
                            return matchDesc;
                        }

                        public void setMatchDesc(String matchDesc) {
                            this.matchDesc = matchDesc;
                        }

                        public String getMatchFormat() {
                            return matchFormat;
                        }

                        public void setMatchFormat(String matchFormat) {
                            this.matchFormat = matchFormat;
                        }

                        public String getStartDate() {
                            return startDate;
                        }

                        public void setStartDate(String startDate) {
                            this.startDate = startDate;
                        }

                        public String getEndDate() {
                            return endDate;
                        }

                        public void setEndDate(String endDate) {
                            this.endDate = endDate;
                        }

                        public String getState() {
                            return state;
                        }

                        public void setState(String state) {
                            this.state = state;
                        }

                        public String getStatus() {
                            return status;
                        }

                        public void setStatus(String status) {
                            this.status = status;
                        }

                        public Team getTeam1() {
                            return team1;
                        }

                        public void setTeam1(Team team1) {
                            this.team1 = team1;
                        }

                        public Team getTeam2() {
                            return team2;
                        }

                        public void setTeam2(Team team2) {
                            this.team2 = team2;
                        }

                        public VenueInfo getVenueInfo() {
                            return venueInfo;
                        }

                        public void setVenueInfo(VenueInfo venueInfo) {
                            this.venueInfo = venueInfo;
                        }

                        public int getCurrBatTeamId() {
                            return currBatTeamId;
                        }

                        public void setCurrBatTeamId(int currBatTeamId) {
                            this.currBatTeamId = currBatTeamId;
                        }

                        public String getSeriesStartDt() {
                            return seriesStartDt;
                        }

                        public void setSeriesStartDt(String seriesStartDt) {
                            this.seriesStartDt = seriesStartDt;
                        }

                        public String getSeriesEndDt() {
                            return seriesEndDt;
                        }

                        public void setSeriesEndDt(String seriesEndDt) {
                            this.seriesEndDt = seriesEndDt;
                        }

                        public boolean isTimeAnnounced() {
                            return isTimeAnnounced;
                        }

                        public void setTimeAnnounced(boolean timeAnnounced) {
                            isTimeAnnounced = timeAnnounced;
                        }

                        public String getStateTitle() {
                            return stateTitle;
                        }

                        public void setStateTitle(String stateTitle) {
                            this.stateTitle = stateTitle;
                        }

                        public boolean isFantasyEnabled() {
                            return isFantasyEnabled;
                        }

                        public void setFantasyEnabled(boolean fantasyEnabled) {
                            isFantasyEnabled = fantasyEnabled;
                        }

                        // Nested class Team
                        public static class Team {
                            private int teamId;
                            private String teamName;
                            private String teamSName;
                            private int imageId;

                            public int getTeamId() {
                                return teamId;
                            }

                            public void setTeamId(int teamId) {
                                this.teamId = teamId;
                            }

                            public String getTeamName() {
                                return teamName;
                            }

                            public void setTeamName(String teamName) {
                                this.teamName = teamName;
                            }

                            public String getTeamSName() {
                                return teamSName;
                            }

                            public void setTeamSName(String teamSName) {
                                this.teamSName = teamSName;
                            }

                            public int getImageId() {
                                return imageId;
                            }

                            public void setImageId(int imageId) {
                                this.imageId = imageId;
                            }
                        }

                        // Nested class VenueInfo
                        public static class VenueInfo {
                            private int id;
                            private String ground;
                            private String city;
                            private String timezone;
                            private String latitude;
                            private String longitude;

                            public int getId() {
                                return id;
                            }

                            public void setId(int id) {
                                this.id = id;
                            }

                            public String getGround() {
                                return ground;
                            }

                            public void setGround(String ground) {
                                this.ground = ground;
                            }

                            public String getCity() {
                                return city;
                            }

                            public void setCity(String city) {
                                this.city = city;
                            }

                            public String getTimezone() {
                                return timezone;
                            }

                            public void setTimezone(String timezone) {
                                this.timezone = timezone;
                            }

                            public String getLatitude() {
                                return latitude;
                            }

                            public void setLatitude(String latitude) {
                                this.latitude = latitude;
                            }

                            public String getLongitude() {
                                return longitude;
                            }

                            public void setLongitude(String longitude) {
                                this.longitude = longitude;
                            }
                        }
                    }

                    // Nested class MatchScore
                    public static class MatchScore {
                        private TeamScore team1Score;
                        private TeamScore team2Score;

                        public TeamScore getTeam1Score() {
                            return team1Score;
                        }

                        public void setTeam1Score(TeamScore team1Score) {
                            this.team1Score = team1Score;
                        }

                        public TeamScore getTeam2Score() {
                            return team2Score;
                        }

                        public void setTeam2Score(TeamScore team2Score) {
                            this.team2Score = team2Score;
                        }

                        // Nested class TeamScore
                        public static class TeamScore {
                            private Innings inngs1;

                            public Innings getInngs1() {
                                return inngs1;
                            }

                            public void setInngs1(Innings inngs1) {
                                this.inngs1 = inngs1;
                            }

                            // Nested class Innings
                            public static class Innings {
                                private String inningsId;
                                private String runs;
                                private String wickets;
                                private String overs;

                                public String getInningsId() {
                                    return inningsId;
                                }

                                public void setInningsId(String inningsId) {
                                    this.inningsId = inningsId;
                                }

                                public String getRuns() {
                                    return runs;
                                }

                                public void setRuns(String runs) {
                                    this.runs = runs;
                                }

                                public String getWickets() {
                                    return wickets;
                                }

                                public void setWickets(String wickets) {
                                    this.wickets = wickets;
                                }

                                public String getOvers() {
                                    return overs;
                                }

                                public void setOvers(String overs) {
                                    this.overs = overs;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
