CREATE TABLE public.employees (
	id UUID NOT NULL PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	address VARCHAR(256),
	postal_code VARCHAR(35),
	country VARCHAR(100),
	phone_number VARCHAR(100),
	gross_salary REAL,
	taxes REAL,
	net_salary REAL
)