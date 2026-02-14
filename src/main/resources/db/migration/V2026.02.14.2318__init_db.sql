CREATE TABLE IF NOT EXISTS users (
        id BIGSERIAL PRIMARY KEY,
        login VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS task (
       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
       user_id BIGINT NOT NULL,
       status VARCHAR(20) NOT NULL,
       source_text TEXT NOT NULL,
       corrected_text TEXT,
       language VARCHAR(2) NOT NULL,
       error_message TEXT,
       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);