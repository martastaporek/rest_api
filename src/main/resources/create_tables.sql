-- Table: public.mundials

-- DROP TABLE public.mundials;

CREATE TABLE public.mundials
(
  id integer NOT NULL DEFAULT nextval('mundials_id_seq'::regclass),
  name text,
  location text,
  year integer,
  CONSTRAINT mundials_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.mundials
  OWNER TO team_a;

  -- Table: public.teams

  -- DROP TABLE public.teams;

  CREATE TABLE public.teams
  (
    id integer NOT NULL DEFAULT nextval('team_id_seq'::regclass),
    name text,
    CONSTRAINT team_pkey PRIMARY KEY (id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.teams
    OWNER TO team_a;

    -- Table: public.players

    -- DROP TABLE public.players;

    CREATE TABLE public.players
    (
      id integer NOT NULL DEFAULT nextval('players_id_seq'::regclass),
      first_name text,
      last_name text,
      birth_year integer,
      CONSTRAINT players_pkey PRIMARY KEY (id)
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE public.players
      OWNER TO team_a;

      -- Table: public.matches

      -- DROP TABLE public.matches;

      CREATE TABLE public.matches
      (
        id integer NOT NULL DEFAULT nextval('matches_id_seq'::regclass),
        first_team_id integer NOT NULL DEFAULT nextval('matches_first_team_id_seq'::regclass),
        second_team_id integer NOT NULL DEFAULT nextval('matches_second_team_id_seq'::regclass),
        first_team_score integer,
        second_team_score integer,
        date date,
        location text,
        CONSTRAINT matches_pkey PRIMARY KEY (id),
        CONSTRAINT matches_first_team_id_fkey FOREIGN KEY (first_team_id)
            REFERENCES public.matches (id) MATCH SIMPLE
            ON UPDATE CASCADE ON DELETE CASCADE,
        CONSTRAINT matches_second_team_id_fkey FOREIGN KEY (second_team_id)
            REFERENCES public.teams (id) MATCH SIMPLE
            ON UPDATE CASCADE ON DELETE CASCADE
      )
      WITH (
        OIDS=FALSE
      );
      ALTER TABLE public.matches
        OWNER TO team_a;
        -- Table: public.players_in_teams

        -- DROP TABLE public.players_in_teams;

        CREATE TABLE public.players_in_teams
        (
          id integer NOT NULL DEFAULT nextval('players_in_teams_id_seq'::regclass),
          player_id integer NOT NULL DEFAULT nextval('players_in_teams_player_id_seq'::regclass),
          team_id integer NOT NULL DEFAULT nextval('players_in_teams_team_id_seq'::regclass),
          CONSTRAINT players_in_teams_pkey PRIMARY KEY (id),
          CONSTRAINT players_in_teams_player_id_fkey FOREIGN KEY (player_id)
              REFERENCES public.players (id) MATCH SIMPLE
              ON UPDATE CASCADE ON DELETE CASCADE,
          CONSTRAINT players_in_teams_team_id_fkey FOREIGN KEY (team_id)
              REFERENCES public.teams (id) MATCH SIMPLE
              ON UPDATE CASCADE ON DELETE CASCADE
        )
        WITH (
          OIDS=FALSE
        );
        ALTER TABLE public.players_in_teams
          OWNER TO team_a;

          -- Table: public.matches_in_mundials

          -- DROP TABLE public.matches_in_mundials;

          CREATE TABLE public.matches_in_mundials
          (
            id integer NOT NULL DEFAULT nextval('matches_in_mundials_id_seq'::regclass),
            match_id integer NOT NULL DEFAULT nextval('matches_in_mundials_match_id_seq'::regclass),
            mundial_id integer NOT NULL DEFAULT nextval('matches_in_mundials_mundial_id_seq'::regclass),
            CONSTRAINT matches_in_mundials_pkey PRIMARY KEY (id),
            CONSTRAINT matches_in_mundials_match_id_fkey FOREIGN KEY (match_id)
                REFERENCES public.matches (id) MATCH SIMPLE
                ON UPDATE CASCADE ON DELETE CASCADE,
            CONSTRAINT matches_in_mundials_mundial_id_fkey FOREIGN KEY (mundial_id)
                REFERENCES public.mundials (id) MATCH SIMPLE
                ON UPDATE CASCADE ON DELETE CASCADE
          )
          WITH (
            OIDS=FALSE
          );
          ALTER TABLE public.matches_in_mundials
            OWNER TO team_a;
