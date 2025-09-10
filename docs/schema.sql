create table if not exists routes (
  id bigint primary key generated always as identity,
  name text not null,
  observation text,
  unique (name)
);

create table if not exists vehicles (
  id bigint primary key generated always as identity,
  plate text unique not null,
  type text,
  capacity_kg int,
  year smallint,
  observation text
);

create table if not exists waste_categories (
  id bigint primary key generated always as identity,
  name text unique not null
);

create table if not exists collection_runs (
  id bigint primary key generated always as identity,
  route_id bigint not null references routes (id),
  vehicle_id bigint references vehicles (id),
  date date not null,
  month smallint not null check (month between 1 and 12),
  year smallint not null
);

create table if not exists collection_records (
  id bigint primary key generated always as identity,
  run_id bigint references collection_runs (id),
  route_id bigint not null references routes (id),
  waste_cat_id bigint not null references waste_categories (id),
  month smallint not null check (month between 1 and 12),
  year smallint not null,
  tons numeric(12, 3) not null,
  unique (route_id, waste_cat_id, month, year)
);

create table if not exists fuel_logs (
  id bigint primary key generated always as identity,
  vehicle_id bigint not null references vehicles (id),
  date date,
  month smallint check (month between 1 and 12),
  year smallint,
  kilometers numeric(12, 2),
  liters numeric(12, 2),
  cost numeric(12, 2)
);

create table if not exists drivers (
  id bigint primary key generated always as identity,
  name text not null
);

create table if not exists vehicle_drivers (
  vehicle_id bigint not null references vehicles (id),
  driver_id bigint not null references drivers (id),
  start_date date,
  end_date date,
  primary key (vehicle_id, driver_id, start_date)
);

create table if not exists run_drivers (
  run_id bigint not null references collection_runs (id),
  driver_id bigint not null references drivers (id),
  role text,
  primary key (run_id, driver_id)
);

create table if not exists route_categories (
  route_id bigint not null references routes (id),
  waste_cat_id bigint not null references waste_categories (id),
  primary key (route_id, waste_cat_id)
);