'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable('user_projects', { 
      id: {
         type: Sequelize.INTEGER,
         primaryKey: true,
         autoIncrement: true,
         allowNull: false
     },
     user_id: {
      type: Sequelize.INTEGER,
      references: {
        model: {
          tableName: 'users'          
        },
        key: 'id'
      },
      allowNull: false
     },
     project_id: {
      type: Sequelize.INTEGER,
      references: {
        model: {
          tableName: 'projects'
        },
        key: 'id'
      },
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
    await queryInterface.dropTable('user_projects');
  }
};