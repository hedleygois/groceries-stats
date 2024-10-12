CREATE INDEX idx_payment_type
ON payment_types (type text_pattern_ops);