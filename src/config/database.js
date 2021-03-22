module.exports = {
    dialect: 'postgres',
    host: 'db',
    username: process.env.DATABASE_USERNAME,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE_DATABASE,
    define: {
        timestamps: true,
        underscored: true //difine name of snake case (seperate with _ )
    }
};