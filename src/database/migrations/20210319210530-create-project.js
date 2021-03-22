'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
     await queryInterface.createTable('projects', { 
       id: {
          type: Sequelize.INTEGER,
          primaryKey: true,
          autoIncrement: true,
          allowNull: false
      },
      name: {
        type: Sequelize.STRING,
        allowNull: false 
      },
      created_at: {
        type: Sequelize.DATE,
        allowNull: false 
      }, 
      update_at: {
        type: Sequelize.DATE,
        allowNull: false 
      }
      });
  },

  down: async (queryInterface, Sequelize) => {
     await queryInterface.dropTable('projects');
  }
};