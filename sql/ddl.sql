-- Dialect: PostgreSQL
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "users" (
  "id" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "name" VARCHAR(255) NOT NULL,
  "document" VARCHAR(32) UNIQUE NOT NULL,
  "email" VARCHAR(255) UNIQUE NOT NULL,
  "password" VARCHAR(255) NOT NULL,
  "shopkeeper" BOOLEAN NOT NULL DEFAULT false,
  -- Balance is in cents
  "balance" INTEGER NOT NULL DEFAULT 0,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS "transactions" (
  "id" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  "payer_id" UUID NOT NULL,
  "payee_id" UUID NOT NULL,
  "value" INTEGER NOT NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT "fk_payer_id" FOREIGN KEY ("payer_id") REFERENCES "users" ("id"),
  CONSTRAINT "fk_payee_id" FOREIGN KEY ("payee_id") REFERENCES "users" ("id")
);

CREATE INDEX IF NOT EXISTS "idx_payer_id" ON "transactions" ("payer_id");

CREATE INDEX IF NOT EXISTS "idx_payee_id" ON "transactions" ("payee_id");